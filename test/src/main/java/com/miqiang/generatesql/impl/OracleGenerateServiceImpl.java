package com.miqiang.generatesql.impl;

import com.miqiang.entity.Column;
import com.miqiang.entity.Table;
import com.miqiang.generatesql.GenerateService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * oracle数据库生成sql集合服务接口
 * @author miqiang
 * @date 2018/12/7
 * version      1.0
 */
public class OracleGenerateServiceImpl implements GenerateService {

    @Override
    public String getDBType() {
        return "oracle";
    }

    @Override
    public List<String> generateSql(String schema, List<Table> tables) {
        List<String> sqls = new ArrayList<>();
        if (null != tables && tables.size() > 0){
            for (Table table : tables){
                String sql = getTableSql(schema, table.getName(), table.getDocumentation());
                sqls.add(sql);
                List<Column> columns = table.getColumns();
                if (null != columns && columns.size() > 0){
                    for (Column column : columns){
                        sqls.add(getColumnSql(schema, table.getName(), column.getName(), column.getDocumentation()));
                    }
                }
            }
        }
        return sqls;
    }

    /**
     * 获取表注释的sql
     * @param schema        模式名称
     * @param name          表名称
     * @param documentation 注释内容
     * @return  表注释sql
     */
    private String getTableSql(String schema, String name, String documentation) {
        if (StringUtils.isNotBlank(schema)) {
            return "COMMENT ON TABLE " + schema + "." + name + " IS '" + documentation + "'";
        }
        return "COMMENT ON TABLE " + name + " IS '" + documentation + "'";
    }

    /**
     * 获取字段注释的sql
     * @param schema        模式名称
     * @param tableName     表名称
     * @param columnName    字段名称
     * @param documentation 注释内容
     * @return  字段注释sql
     */
    private String getColumnSql(String schema, String tableName, String columnName, String documentation) {
        if (StringUtils.isNotBlank(schema)) {
            return "COMMENT ON COLUMN " + schema + "." + tableName + "." + columnName + " IS '" + documentation + "'";
        }
        return "COMMENT ON COLUMN " + tableName + "." + columnName + " IS '" + documentation + "'";
    }
}
