package com.wcc.platform.streaming.flink.core.sql;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.util.StringUtil;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.calcite.sql.SqlKind.IDENTIFIER;

/**
 * 解析临时视图
 *
 * @author wangwei
 * @since 2021-09-10
 */
public class CreateViewParser implements IParser {

    private static final String PATTERN_STR = "(?i)create\\s+view\\s+([^\\s]+)\\s+as\\s+select\\s+(.*)";

    private static final String EMPTY_STR = "(?i)^\\screate\\s+view\\s+(\\S+)\\s*\\((.+)\\)$";

    private static final Pattern NONEMPTYVIEW = Pattern.compile(PATTERN_STR);

    private static final Pattern EMPTYVIEW = Pattern.compile(EMPTY_STR);

    public static CreateViewParser newInstance() {
        return new CreateViewParser();
    }

    @Override
    public boolean verify(String sql) {
        if (Pattern.compile(EMPTY_STR).matcher(sql).find()) {
            return true;
        }
        return NONEMPTYVIEW.matcher(sql).find();
    }

    @Override
    public void parse(String sql, SqlTree sqlTree) {
        if (NONEMPTYVIEW.matcher(sql).find()) {
            Matcher matcher = NONEMPTYVIEW.matcher(sql);
            String tableName = null;
            String selectSql = null;
            if (matcher.find()) {
                tableName = matcher.group(1);
                selectSql = "select " + matcher.group(2);
            }

            org.apache.calcite.sql.parser.SqlParser.Config config = org.apache.calcite.sql.parser.SqlParser
                    .configBuilder()
                    .setLex(Lex.MYSQL)
                    .build();
            org.apache.calcite.sql.parser.SqlParser sqlParser = SqlParser.create(selectSql, config);

            SqlNode sqlNode = null;
            try {
                sqlNode = sqlParser.parseStmt();
            } catch (SqlParseException e) {
                throw new PlatformStreamingException(e);
            }

            SqlParserResult sqlParseResult = new SqlParserResult();
            parseNode(sqlNode, sqlParseResult);

            sqlParseResult.setTableName(tableName);
            String transformSelectSql = StringUtil.replaceIgnoreQuota(sqlNode.toString(), "`", "");
            sqlParseResult.setExecSql(transformSelectSql);
            sqlTree.addTmpSql(sqlParseResult);
            sqlTree.addTmplTableInfo(tableName, sqlParseResult);
        } else {
            if (EMPTYVIEW.matcher(sql).find()) {
                Matcher matcher = EMPTYVIEW.matcher(sql);
                String tableName = null;
                String fieldsInfoStr = null;
                if (matcher.find()) {
                    tableName = matcher.group(1);
                    fieldsInfoStr = matcher.group(2);
                }
                SqlParserResult sqlParseResult = new SqlParserResult();
                sqlParseResult.setFieldsInfoStr(fieldsInfoStr);
                sqlParseResult.setTableName(tableName);
                sqlTree.addTmplTableInfo(tableName, sqlParseResult);
            }

        }
    }

    private static void parseNode(SqlNode sqlNode, SqlParserResult sqlParseResult) {
        SqlKind sqlKind = sqlNode.getKind();
        switch (sqlKind) {
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
            default:
                //do nothing
                break;
        }
    }


    public static class SqlParserResult {
        /**
         * 表名
         */
        private String tableName;

        /**
         * 字段
         */
        private String fieldsInfoStr;

        /**
         * 执行的sql
         */
        private String execSql;

        /**
         * 源表
         */
        private List<String> sourceTableList = new ArrayList<>();

        public void addSourceTable(String sourceTable) {
            sourceTableList.add(sourceTable);
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getFieldsInfoStr() {
            return fieldsInfoStr;
        }

        public void setFieldsInfoStr(String fieldsInfoStr) {
            this.fieldsInfoStr = fieldsInfoStr;
        }

        public String getExecSql() {
            return execSql;
        }

        public void setExecSql(String execSql) {
            this.execSql = execSql;
        }

        public List<String> getSourceTableList() {
            return sourceTableList;
        }

    }
}
