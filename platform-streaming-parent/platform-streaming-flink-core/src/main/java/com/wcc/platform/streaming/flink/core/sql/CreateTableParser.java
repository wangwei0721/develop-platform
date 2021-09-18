package com.wcc.platform.streaming.flink.core.sql;

import com.wcc.platform.util.ArgsUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析定义表的sql
 *
 * @author wangwei
 * @since 2021-09-10
 */
public class CreateTableParser implements IParser {

    private static final String PATTERN_STR = "(?i)create\\s+table\\s+(\\S+)\\s*\\((.+)\\)\\s*with\\s*\\((.+)\\)";

    private static final Pattern PATTERN = Pattern.compile(PATTERN_STR);

    public static CreateTableParser newInstance() {
        return new CreateTableParser();
    }

    @Override
    public boolean verify(String sql) {
        return PATTERN.matcher(sql).find();
    }

    @Override
    public void parse(String sql, SqlTree sqlTree) {
        Matcher matcher = PATTERN.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String fieldsInfoStr = matcher.group(2);
            String propsStr = matcher.group(3);
            Map<String, String> props = ArgsUtil.convert2Map(new String[]{propsStr});

            SqlParserResult result = new SqlParserResult();
            result.setTableName(tableName);
            result.setFieldsInfoStr(fieldsInfoStr);
            result.setPropMap(props);

            sqlTree.addPreDealTableInfo(tableName, result);
        }
    }


    public static class SqlParserResult {
        /**
         * 表名
         */
        private String tableName;

        /**
         * 字段名
         */
        private String fieldsInfoStr;

        /**
         * 其他属性，根据不同数据源类型存储不同的数据
         */
        private Map<String, String> propMap;

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

        public Map<String, String> getPropMap() {
            return propMap;
        }

        public void setPropMap(Map<String, String> propMap) {
            this.propMap = propMap;
        }
    }
}
