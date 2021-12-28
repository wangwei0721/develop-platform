package com.wcc.platform.streaming.flink.v10.connecotr.sink.console;

import com.wcc.platform.streaming.flink.core.sink.SinkTableInfo;
import com.wcc.platform.streaming.flink.core.sink.StreamSinkGener;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.table.sinks.RetractStreamTableSink;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.types.Row;

import java.io.Serializable;

public class ConsoleSink implements RetractStreamTableSink<Row>, StreamSinkGener<ConsoleSink>, Serializable {

    @Override
    public ConsoleSink genStreamSink(SinkTableInfo sinkTableInfo) {
        return null;
    }

    @Override
    public TypeInformation<Row> getRecordType() {
        return null;
    }

    @Override
    public void emitDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {

    }

    @Override
    public DataStreamSink<?> consumeDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
        return dataStream.print();
    }

    @Override
    public TableSink<Tuple2<Boolean, Row>> configure(String[] strings, TypeInformation<?>[] typeInformations) {
        return null;
    }
}
