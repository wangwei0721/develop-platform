package com.wcc.platform.streaming.flink.core.v10.source.console;

import org.apache.flink.api.common.io.RichOutputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

import java.io.IOException;

public class ConsoleOutputFormat extends RichOutputFormat<Tuple2> {

    @Override
    public void configure(Configuration configuration) {

    }

    @Override
    public void open(int taskNumber, int numTasks) throws IOException {

    }

    @Override
    public void writeRecord(Tuple2 tuple2) throws IOException {
        System.out.println(tuple2);
    }

    @Override
    public void close() throws IOException {

    }

}
