package com.wcc.platform.streaming.flink.core.table;

import com.wcc.platform.util.ClassUtil;
import com.wcc.platform.util.StringUtil;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Maps;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbsTableParser {

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
    public abstract TableInfo getTableInfo(String tableName, String fieldsInfo, Map<String, String> props);

    public static final String PRIMARY_KEY = "primaryKey";

    private static Pattern primaryKeyPattern = Pattern.compile("(?i)PRIMARY\\s+KEY\\s*\\((.*)\\)");

    public static Map<String, Pattern> keyPatternMap = Maps.newHashMap();

    static {
        keyPatternMap.put(PRIMARY_KEY, primaryKeyPattern);
    }

    public void parseFieldsInfo(String fieldsInfo, TableInfo tableInfo) {

        String[] fieldRows = StringUtil.splitIgnoreQuotaBrackets(fieldsInfo, ",");
        for (String fieldRow : fieldRows) {
            fieldRow = fieldRow.trim();

            boolean isMatcherKey = dealKeyPattern(fieldRow, tableInfo);

            if (isMatcherKey) {
                continue;
            }

            String[] filedInfoArr = fieldRow.split("\\s+");
            if (filedInfoArr.length < 2) {
                throw new RuntimeException(
                        String.format("table [%s] field [%s] format error.", tableInfo.getName(), fieldRow));
            }

            // Compatible situation may arise in space in the fieldName
            String[] filedNameArr = new String[filedInfoArr.length - 1];
            System.arraycopy(filedInfoArr, 0, filedNameArr, 0, filedInfoArr.length - 1);
            String fieldName = String.join(" ", filedNameArr);
            String fieldType = filedInfoArr[filedInfoArr.length - 1].trim();
            Class fieldClass = ClassUtil.stringConvertClass(fieldType);

            tableInfo.addPhysicalMappings(filedInfoArr[0], filedInfoArr[0]);
            tableInfo.addField(fieldName);
            tableInfo.addFieldClass(fieldClass);
            tableInfo.addFieldType(fieldType);
            tableInfo.addFieldExtraInfo(null);
        }

        tableInfo.finish();
    }

}