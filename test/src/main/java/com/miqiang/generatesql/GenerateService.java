package com.miqiang.generatesql;

import com.miqiang.entity.Table;

import java.util.List;

/**
 * 生成sql集合服务接口
 * @author miqiang
 * @date 2018/12/7
 * version      1.0
 */
public interface GenerateService {

    /**
     * 获取数据库类型
     * @return  数据库类型
     */
    String getDBType();

    /**
     * 生成sql集合
     * @param tables    表信息集合
     * @return  sql集合
     */
    List<String> generateSql(String schema, List<Table> tables);
}
