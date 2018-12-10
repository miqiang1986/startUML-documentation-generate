package com.miqiang.entity;

import java.util.List;

/**
 * @author miqiang
 * @date 2018/12/7
 * version      1.0
 */
public class Table {

    /**
     * 表名称
     */
    private String name;

    /**
     * 表注释
     */
    private String documentation;

    /**
     * 字段集合
     */
    private List<Column> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", documentation='" + documentation + '\'' +
                ", columns=" + columns +
                '}';
    }
}
