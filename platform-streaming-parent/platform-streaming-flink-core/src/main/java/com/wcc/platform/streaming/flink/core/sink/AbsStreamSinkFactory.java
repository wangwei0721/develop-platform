package com.wcc.platform.streaming.flink.core.sink;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.table.AbsTableParser;

public abstract class AbsStreamSinkFactory {

    public static String CURR_TYPE = "sink";

    private static final String DIR_NAME_FORMAT = "%ssink";

    public static AbsTableParser getSqlParser(String pluginType, String sqlRootDir) {
        try {
            // todo 根据pluginType动态生成类路径
            Class<?> clazz = Class.forName(pluginType);
            if (!clazz.isAssignableFrom(AbsTableParser.class)) {
                throw new PlatformStreamingException("class " + clazz.getName() + " not subClass of AbsTableParser");
            }
            return clazz.asSubclass(AbsTableParser.class).newInstance();
        } catch (Exception e) {
            throw new PlatformStreamingException(e);
        }
    }

    public abstract <T> T getTableSink(SinkTableInfo sinkTableInfo);
}