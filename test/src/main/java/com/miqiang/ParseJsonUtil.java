package com.miqiang;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.miqiang.entity.Column;
import com.miqiang.entity.Table;
import com.miqiang.generatesql.GenerateService;
import com.miqiang.util.FileUtil;
import com.miqiang.util.JsonUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 *
 * @author miqiang
 * @date 2018/12/7
 * version      1.0
 */
public class ParseJsonUtil {

    /**
     * 要解析的集合节点名称
     */
    private static final String OWNED_ELEMENTS = "ownedElements";

    /**
     * type节点名称
     */
    private static final String TYPE_NODE_NAME = "_type";

    /**
     * type节点值
     */
    private static final String TYPE_NODE_VALUE = "ERDEntity";

    /**
     * 字段节点名称
     */
    private static final String COLUMN_NODE_NAME ="columns";

    List<Table> tables = new ArrayList<>();

    /**
     * 解析文件中的json，并将表和字段注释写入到指定文件中
     * @param file      json文件
     * @param writeFile 要写入sql的文件
     * @param dbType    数据库类型。支持大小写转换
     * @param schema    数据库模式名称
     */
    public void excute(String file, String writeFile, String dbType, String schema) {
        //读取文件
        String json = FileUtil.readString(new File(file));
        // 解析文件中的json
        getTables(json);
        System.out.println("共"+tables.size()+"张表");
        // 获取数据库服务
        GenerateService service = getGenerateService(dbType);
        if (null == service){
            throw new RuntimeException("暂时不支持该数据库类型的转换！");
        }
        // 生成sql
        List<String> sqls = service.generateSql(schema, tables);
        if (null != sqls && !sqls.isEmpty()){
            // 将sql语句转成字符串
            StringBuilder strs = new StringBuilder();
            for (String sql : sqls){
                strs.append("\r\n"+sql);
            }
            // 将字符串写入到文件中
            FileUtil.append(writeFile, strs.toString());
        }
    }

    /**
     * 获取支持生成sql的服务接口
     * 待优化成注解获取(仿spring)
     * @param dbType    数据库类型。支持大小写转换
     * @return  支持生成sql的服务接口
     */
    private GenerateService getGenerateService(String dbType){
        ServiceLoader<GenerateService> loader = ServiceLoader.load(GenerateService.class);
        for (GenerateService service : loader) {
            // 支持转换大小写
            if (dbType.equals(service.getDBType())
                    || dbType.toUpperCase().equals(service.getDBType())) {
                return service;
            }
        }
        return null;
    }

    /**
     * 获取表信息集合
     * @param ownedElements 主节点中集合节点的值
     */
    private void getTables(String ownedElements){
        if (JsonUtil.isJsonObject(ownedElements)){
            JSONObject object = JSONObject.parseObject(ownedElements);
            if (null != object) {
                if (TYPE_NODE_VALUE.equals(object.getString(TYPE_NODE_NAME))) {
                    // 解析表
                    Table table = JSONObject.parseObject(ownedElements, Table.class);
                    // 解析字段
                    JSONArray columns = object.getJSONArray(COLUMN_NODE_NAME);
                    if (null != columns && !columns.isEmpty()){
                        List<Column> columnList = new ArrayList<>();
                        for (Object obj : columns){
                            if (JsonUtil.isJsonObject(String.valueOf(obj))){
                                Column column = JSONObject.parseObject(String.valueOf(obj), Column.class);
                                columnList.add(column);
                            }
                        }
                        table.setColumns(columnList);
                    }
                    tables.add(table);
                    return;
                }
                String str = object.getString(OWNED_ELEMENTS);
                if (StringUtils.isNotBlank(str)) {
                    getTables(str);
                }
            }
        }else {
            // TODO 验证array
            JSONArray array = JSONArray.parseArray(ownedElements);
            for (Object obj : array) {
                String str = String.valueOf(obj);
                if (StringUtils.isNotBlank(str)) {
                    getTables(str);
                }
            }
        }
    }

}
