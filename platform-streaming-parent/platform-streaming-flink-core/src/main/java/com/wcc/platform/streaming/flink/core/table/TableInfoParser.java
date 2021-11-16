package com.wcc.platform.streaming.flink.core.table;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.enums.TableType;
import com.wcc.platform.streaming.flink.core.side.SideTableInfo;
import com.wcc.platform.streaming.flink.core.side.StreamSideFactory;
import com.wcc.platform.streaming.flink.core.sink.AbsStreamSinkFactory;
import com.wcc.platform.streaming.flink.core.source.AbsStreamSourceFactory;
import com.wcc.platform.streaming.flink.core.sql.CreateTableParser;
import com.wcc.platform.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析TableInfo的统一入口
 *
 * @author wangwei
 * @since 2021-09-18
 */
public class TableInfoParser {

    private final static String TYPE_KEY = "type";

    private final static String SIDE_TABLE_SIGN = "(?i)^PERIOD\\s+FOR\\s+SYSTEM_TIME$";

    private final static Pattern SIDE_PATTERN = Pattern.compile(SIDE_TABLE_SIGN);

    /**
     * 进程内缓存AbsTableParser对象
     */
    private Map<String, AbsTableParser> sourceTableInfoMap = new ConcurrentHashMap<>();
    private Map<String, AbsTableParser> targetTableInfoMap = new ConcurrentHashMap<>();
    private Map<String, AbsTableParser> sideTableInfoMap = new ConcurrentHashMap<>();

    /**
     * 根据对应的表类型，获取对一个的AbsTableParser，实际解析由对应AbsTableParser完成
     *
     * @param tableType       tableType
     * @param parserResult    parserResult
     * @param localPluginRoot localPluginRoot
     * @return com.wcc.platform.streaming.flink.core.table.TableInfo
     * @author wangwei
     * @since 2021-09-18
     */
    public TableInfo parseWithTableType(int tableType, CreateTableParser.SqlParserResult parserResult,
                                        String localPluginRoot) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        AbsTableParser absTableParser = null;
        Map<String, String> props = parserResult.getPropMap();
        String type = props.get(TYPE_KEY);

        if (!StringUtil.isNotBlank(type)) {
            throw new PlatformStreamingException("create table statement requires property of type");
        }

        if (tableType == TableType.SOURCE.getType()) {
            boolean isSideTable = checkIsSideTable(parserResult.getFieldsInfoStr());

            if (!isSideTable) {
                absTableParser = sourceTableInfoMap.get(type);
                if (absTableParser == null) {
                    absTableParser = AbsStreamSourceFactory.getSqlParser(type, localPluginRoot);
                    sourceTableInfoMap.put(type, absTableParser);
                }
            } else {
                absTableParser = sideTableInfoMap.get(type);
                if (absTableParser == null) {
                    String cacheType = props.get(SideTableInfo.CACHE_KEY);
                    absTableParser = StreamSideFactory.getSqlParser(type, localPluginRoot, cacheType);
                    sideTableInfoMap.put(type, absTableParser);
                }
            }

        } else if (tableType == TableType.SINK.getType()) {
            absTableParser = targetTableInfoMap.get(type);
            if (absTableParser == null) {
                absTableParser = AbsStreamSinkFactory.getSqlParser(type, localPluginRoot);
                targetTableInfoMap.put(type, absTableParser);
            }
        }

        if (absTableParser == null) {
            throw new PlatformStreamingException(String.format("not support %s type of table", type));
        }

        Map<String, String> prop = new HashMap<>(props.size());
        //Shield case
        props.forEach((key, val) -> prop.put(key.toLowerCase(), val));
        return absTableParser.getTableInfo(parserResult.getTableName(), parserResult.getFieldsInfoStr(), prop);
    }

    private static boolean checkIsSideTable(String tableField) {
        String[] fieldInfos = tableField.split(",");
        for (String field : fieldInfos) {
            Matcher matcher = SIDE_PATTERN.matcher(field.trim());
            if (matcher.find()) {
                return true;
            }
        }

        return false;
    }

}
