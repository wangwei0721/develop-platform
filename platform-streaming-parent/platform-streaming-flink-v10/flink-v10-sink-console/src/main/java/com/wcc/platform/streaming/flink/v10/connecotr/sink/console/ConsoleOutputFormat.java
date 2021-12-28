package com.wcc.platform.streaming.flink.v10.connecotr.sink.console;

import org.apache.flink.api.common.io.RichOutputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.types.Row;

import java.io.IOException;

public class ConsoleOutputFormat extends RichOutputFormat<Tuple2<Boolean, Row>> {

    @Override
    public void configure(Configuration configuration) {

    }

    @Override
    public void open(int taskNumber, int numTasks) throws IOException {

    }

    @Override
    public void writeRecord(Tuple2<Boolean, Row> tuple2) throws IOException {
        System.out.println(tuple2);
    }

    @Override
    public void close() throws IOException {

    }

}
