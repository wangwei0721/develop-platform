package com.wcc.platform.streaming.flink.core.source;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.table.AbsSourceParser;
import com.wcc.platform.streaming.flink.core.table.AbsTableParser;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;

public abstract class AbsStreamSourceFactory {

    private static final String CURR_TYPE = "source";

    private static final String DIR_NAME_FORMAT = "%ssource";

    public static AbsTableParser getSqlParser(String pluginType, String sqlRootDir) {
        try {
            // todo 根据pluginType动态生成类路径
            Class<?> clazz = Class.forName(pluginType);
            if (!clazz.isAssignableFrom(AbsTableParser.class)) {
                throw new RuntimeException("class " + clazz.getName() + " not subClass of AbsTableParser");
            }
            return clazz.asSubclass(AbsSourceParser.class).newInstance();
        } catch (Exception e) {
            throw new PlatformStreamingException(e);
        }
    }

    public abstract <T> T generateStreamSource(SourceTableInfo sourceTableInfo, StreamExecutionEnvironment env,
                                               TableEnvironment tableEnv);

}
