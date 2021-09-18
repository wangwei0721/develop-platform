package com.wcc.platform.streaming.flink.core.sql;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.enums.TableType;
import com.wcc.platform.streaming.flink.core.table.TableInfo;
import com.wcc.platform.streaming.flink.core.table.TableInfoParser;
import com.wcc.platform.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * sql解析
 *
 * @author wangwei
 * @since 2021-09-11
 */
public class SqlParser {

    private static final char SQL_DELIMITER = ';';

    private static String LOCAL_SQL_PLUGIN_ROOT;

    private static List<IParser> sqlParserList = new ArrayList<>();

    static {
        sqlParserList.add(CreateTableParser.newInstance());
        sqlParserList.add(CreateViewParser.newInstance());
        sqlParserList.add(CreateFuncParser.newInstance());
        sqlParserList.add(InsertSqlParser.newInstance());
    }

    public static void setLocalSqlPluginRoot(String localSqlPluginRoot) {
        LOCAL_SQL_PLUGIN_ROOT = localSqlPluginRoot;
    }

    /**
     * sql解析入口
     *
     * @param sql sql
     * @return com.wcc.platform.streaming.flink.core.parser.SqlTree
     * @author wangwei
     * @since 2021-09-11
     */
    public static SqlTree parseSql(String sql) throws Exception {

        if (!StringUtil.isNotBlank(sql)) {
            throw new PlatformStreamingException("sql is not null");
        }

        if (LOCAL_SQL_PLUGIN_ROOT == null) {
            throw new PlatformStreamingException("need to set local sql plugin root");
        }

        // 移除注释，压缩sql
        sql = sql.replaceAll("--.*", "")
                .replaceAll("\r\n", " ")
                .replaceAll("\n", " ")
                .replace("\t", " ").trim();

        List<String> sqlArr = StringUtil.splitIgnoreQuota(sql, SQL_DELIMITER);
        SqlTree sqlTree = new SqlTree();
        TableInfoParser tableInfoParser = new TableInfoParser();
        for (String childSql : sqlArr) {
            if (!StringUtil.isNotBlank(childSql)) {
                continue;
            }
            boolean result = false;
            for (IParser sqlParser : sqlParserList) {
                if (!sqlParser.verify(childSql)) {
                    continue;
                }
                // 根据sql类型逐行解析
                sqlParser.parse(childSql, sqlTree);
                result = true;
                break;
            }
            if (!result) {
                throw new PlatformStreamingException(String.format("%s:Syntax does not support,the format of SQL like insert into tb1 select * from tb2.", childSql));
            }
        }

        //解析exec-sql
        if (sqlTree.getExecSqlList().size() == 0) {
            throw new PlatformStreamingException("sql no executable statement");
        }
        for (InsertSqlParser.SqlParserResult result : sqlTree.getExecSqlList()) {
            List<String> sourceTableList = result.getSourceTableList();
            List<String> targetTableList = result.getTargetTableList();
            Set<String> tmpTableList = sqlTree.getTmpTableMap().keySet();

            for (String tableName : sourceTableList) {
                if (!tmpTableList.contains(tableName)) {
                    CreateTableParser.SqlParserResult createTableResult = sqlTree.getPreDealTableMap().get(tableName);
                    if (createTableResult == null) {
                        throw new PlatformStreamingException(String.format("can't find table %s", tableName));
                    }
                    TableInfo tableInfo = tableInfoParser.parseWithTableType(TableType.SOURCE.getType(),
                            createTableResult, LOCAL_SQL_PLUGIN_ROOT);
                    sqlTree.addTableInfo(tableName, tableInfo);
                }
            }

            for (String tableName : targetTableList) {
                if (!tmpTableList.contains(tableName)) {
                    CreateTableParser.SqlParserResult createTableResult = sqlTree.getPreDealTableMap().get(tableName);
                    if (createTableResult == null) {
                        throw new PlatformStreamingException(String.format("can't find sink table %s", tableName));
                    }

                    TableInfo tableInfo = tableInfoParser.parseWithTableType(TableType.SINK.getType(),
                            createTableResult, LOCAL_SQL_PLUGIN_ROOT);
                    sqlTree.addTableInfo(tableName, tableInfo);
                }
            }
        }

        for (CreateViewParser.SqlParserResult result : sqlTree.getTmpSqlList()) {
            List<String> sourceTableList = result.getSourceTableList();
            for (String tableName : sourceTableList) {
                if (!sqlTree.getTableInfoMap().keySet().contains(tableName)) {
                    CreateTableParser.SqlParserResult createTableResult = sqlTree.getPreDealTableMap().get(tableName);
                    if (createTableResult == null) {
                        CreateViewParser.SqlParserResult tmpTableResult = sqlTree.getTmpTableMap().get(tableName);
                        if (tmpTableResult == null) {
                            throw new PlatformStreamingException(String.format("can't find table %s", tableName));
                        }
                    } else {
                        TableInfo tableInfo = tableInfoParser.parseWithTableType(TableType.SOURCE.getType(),
                                createTableResult, LOCAL_SQL_PLUGIN_ROOT);
                        sqlTree.addTableInfo(tableName, tableInfo);
                    }
                }
            }
        }
        return sqlTree;
    }

}
