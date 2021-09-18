package com.wcc.platform.streaming.flink.core.source;

import com.wcc.platform.streaming.flink.core.table.AbsTableParser;

public class StreamSourceFactory {

    private static final String CURR_TYPE = "source";

    private static final String DIR_NAME_FORMAT = "%ssource";

    public static AbsTableParser getSqlParser(String pluginType, String sqlRootDir) {

        return null;
    }
}
