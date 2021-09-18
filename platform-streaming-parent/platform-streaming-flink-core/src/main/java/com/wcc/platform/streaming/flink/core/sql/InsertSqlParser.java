package com.wcc.platform.streaming.flink.core.sql;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.util.StringUtil;
import org.apache.calcite.config.Lex;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

import java.util.ArrayList;
import java.util.List;

import static org.apache.calcite.sql.SqlKind.IDENTIFIER;

/**
 * 解析转化的sql
 *
 * @author wangwei
 * @since 2021-09-11
 */
public class InsertSqlParser implements IParser {

    public static InsertSqlParser newInstance() {
        return new InsertSqlParser();
    }

    @Override
    public boolean verify(String sql) {
        return StringUtil.isNotBlank(sql) && sql.trim().toLowerCase().startsWith("insert");
    }

    @Override
    public void parse(String sql, SqlTree sqlTree) {
        SqlParser.Config config = SqlParser
                .configBuilder()
                .setLex(Lex.MYSQL)
                .build();
        SqlParser sqlParser = SqlParser.create(sql, config);
        SqlNode sqlNode = null;
        try {
            sqlNode = sqlParser.parseStmt();
        } catch (SqlParseException e) {
            throw new PlatformStreamingException(e);
        }

        SqlParserResult sqlParseResult = new SqlParserResult();
        parseNode(sqlNode, sqlParseResult);
        sqlParseResult.setExecSql(sqlNode.toString());
        sqlTree.addExecSql(sqlParseResult);
    }


    private static void parseNode(SqlNode sqlNode, SqlParserResult sqlParseResult) {
        SqlKind sqlKind = sqlNode.getKind();
        switch (sqlKind) {
            case INSERT:
                SqlNode sqlTarget = ((SqlInsert) sqlNode).getTargetTable();
                SqlNode sqlSource = ((SqlInsert) sqlNode).getSource();
                sqlParseResult.addTargetTable(sqlTarget.toString());
                parseNode(sqlSource, sqlParseResult);
                break;
            case SELECT:
                SqlNode sqlFrom = ((SqlSelect) sqlNode).getFrom();
                if (sqlFrom.getKind() == IDENTIFIER) {
                    sqlParseResult.addSourceTable(sqlFrom.toString());
                } else {
                    parseNode(sqlFrom, sqlParseResult);
                }
                break;
            case JOIN:
                SqlNode leftNode = ((SqlJoin) sqlNode).getLeft();
                SqlNode rightNode = ((SqlJoin) sqlNode).getRight();

                if (leftNode.getKind() == IDENTIFIER) {
                    sqlParseResult.addSourceTable(leftNode.toString());
                } else {
                    parseNode(leftNode, sqlParseResult);
                }

                if (rightNode.getKind() == IDENTIFIER) {
                    sqlParseResult.addSourceTable(rightNode.toString());
                } else {
                    parseNode(rightNode, sqlParseResult);
                }
                break;
            case AS:
                //不解析column,所以 as 相关的都是表
                SqlNode identifierNode = ((SqlBasicCall) sqlNode).getOperands()[0];
                if (identifierNode.getKind() != IDENTIFIER) {
                    parseNode(identifierNode, sqlParseResult);
                } else {
                    sqlParseResult.addSourceTable(identifierNode.toString());
                }
                break;
            case MATCH_RECOGNIZE:
                SqlMatchRecognize node = (SqlMatchRecognize) sqlNode;
                sqlParseResult.addSourceTable(node.getTableRef().toString());
                break;
            case UNION:
                SqlNode unionLeft = ((SqlBasicCall) sqlNode).getOperands()[0];
                SqlNode unionRight = ((SqlBasicCall) sqlNode).getOperands()[1];
                if (unionLeft.getKind() == IDENTIFIER) {
                    sqlParseResult.addSourceTable(unionLeft.toString());
                } else {
                    parseNode(unionLeft, sqlParseResult);
                }
                if (unionRight.getKind() == IDENTIFIER) {
                    sqlParseResult.addSourceTable(unionRight.toString());
                } else {
                    parseNode(unionRight, sqlParseResult);
                }
                break;
            case ORDER_BY:
                SqlOrderBy sqlOrderBy = (SqlOrderBy) sqlNode;
                parseNode(sqlOrderBy.query, sqlParseResult);
                break;
            default:
                //do nothing
                break;
        }
    }

    public static class SqlParserResult {
        /**
         * 源表
         */
        private List<String> sourceTableList = new ArrayList<>();
        /**
         * 目标表
         */
        private List<String> targetTableList = new ArrayList<>();
        /**
         * sql逻辑
         */
        private String execSql;

        public void addSourceTable(String sourceTable) {
            sourceTableList.add(sourceTable);
        }

        public void addTargetTable(String targetTable) {
            targetTableList.add(targetTable);
        }

        public List<String> getSourceTableList() {
            return sourceTableList;
        }


        public List<String> getTargetTableList() {
            return targetTableList;
        }

        public String getExecSql() {
            return execSql;
        }

        public void setExecSql(String execSql) {
            this.execSql = execSql;
        }
    }
}
