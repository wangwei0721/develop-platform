package com.wcc.platform.streaming.flink.core.sql;

import com.wcc.platform.streaming.flink.core.table.TableInfo;

import java.util.*;

/**
 * flinkSql最终的转化结果
 *
 * @author wangwei
 * @since 2021-09-10
 */
public class SqlTree {

    /**
     * 自定义函数解析结果
     */
    private List<CreateFuncParser.SqlParserResult> functionList = new ArrayList<>();

    /**
     * 表解析结果
     */
    private Map<String, CreateTableParser.SqlParserResult> preDealTableMap = new HashMap<>();

    /**
     * 表定义
     */
    private Map<String, TableInfo> tableInfoMap = new LinkedHashMap<>();

    /**
     * 转化sql解析结果
     */
    private List<InsertSqlParser.SqlParserResult> execSqlList = new ArrayList<>();

    /**
     * 临时表解析结果
     */
    private List<CreateViewParser.SqlParserResult> tmpSqlList = new ArrayList<>();

    /**
     * 临时表定义
     */
    private Map<String, CreateViewParser.SqlParserResult> tmpTableMap = new HashMap<>();

    public List<CreateFuncParser.SqlParserResult> getFunctionList() {
        return functionList;
    }

    public Map<String, CreateTableParser.SqlParserResult> getPreDealTableMap() {
        return preDealTableMap;
    }

    public Map<String, CreateViewParser.SqlParserResult> getTmpTableMap() {
        return tmpTableMap;
    }

    public List<InsertSqlParser.SqlParserResult> getExecSqlList() {
        return execSqlList;
    }

    public void addFunc(CreateFuncParser.SqlParserResult func) {
        functionList.add(func);
    }

    public void addPreDealTableInfo(String tableName, CreateTableParser.SqlParserResult table) {
        preDealTableMap.put(tableName, table);
    }

    public void addTmplTableInfo(String tableName, CreateViewParser.SqlParserResult table) {
        tmpTableMap.put(tableName, table);
    }

    public void addExecSql(InsertSqlParser.SqlParserResult execSql) {
        execSqlList.add(execSql);
    }

    public void addTmpSql(CreateViewParser.SqlParserResult tmpSql) {
        tmpSqlList.add(tmpSql);
    }

    public List<CreateViewParser.SqlParserResult> getTmpSqlList() {
        return tmpSqlList;
    }

    public Map<String, TableInfo> getTableInfoMap() {
        return tableInfoMap;
    }

    public void addTableInfo(String tableName, TableInfo tableInfo) {
        tableInfoMap.put(tableName, tableInfo);
    }

}