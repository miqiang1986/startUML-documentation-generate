package com.miqiang.entity;

/**
 * @author miqiang
 * @date 2018/12/7
 * version      1.0
 */
public class Column {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段注释
     */
    private String documentation;

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

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", documentation='" + documentation + '\'' +
                '}';
    }
}
