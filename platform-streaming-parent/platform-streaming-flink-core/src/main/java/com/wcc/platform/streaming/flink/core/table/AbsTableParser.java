package com.wcc.platform.streaming.flink.core.table;

import java.util.Map;

public interface AbsTableParser {

    /**
     * 转化获取TableInfo对象
     *
     * @param tableName  tableName
     * @param fieldsInfo fieldsInfo
     * @param props      props
     * @return com.wcc.platform.streaming.flink.core.table.TableInfo
     * @author wangwei
     * @since 2021-09-18
     */
    TableInfo getTableInfo(String tableName, String fieldsInfo, Map<String, String> props);

}