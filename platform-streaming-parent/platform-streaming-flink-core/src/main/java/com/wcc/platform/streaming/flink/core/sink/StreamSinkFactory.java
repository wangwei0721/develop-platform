package com.wcc.platform.streaming.flink.core.sink;

import com.wcc.platform.streaming.flink.core.table.AbsTableParser;

public class StreamSinkFactory {


    public static String CURR_TYPE = "sink";

    private static final String DIR_NAME_FORMAT = "%ssink";

    public static AbsTableParser getSqlParser(String pluginType, String sqlRootDir) {

        return null;
    }
}