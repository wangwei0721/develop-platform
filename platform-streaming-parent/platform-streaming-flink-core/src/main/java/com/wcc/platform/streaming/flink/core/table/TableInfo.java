package com.wcc.platform.streaming.flink.core.table;

import java.io.Serializable;

/**
 * flinkSql解析后转化的逻辑表对象
 *
 * @author wangwei
 * @since 2021-09-10
 */
public abstract class TableInfo implements Serializable {

    /**
     * 表名
     */
    private String name;

    /**
     * 数据源类型
     */
    private String type;

    /**
     * 字段
     */
    private String[] fields;

    /**
     * 字段类型
     */
    private String[] fieldTypes;

    /**
     * 字段类型类
     */
    private Class<?>[] fieldClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String[] getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(String[] fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    public Class<?>[] getFieldClasses() {
        return fieldClasses;
    }

    public void setFieldClasses(Class<?>[] fieldClasses) {
        this.fieldClasses = fieldClasses;
    }
}
