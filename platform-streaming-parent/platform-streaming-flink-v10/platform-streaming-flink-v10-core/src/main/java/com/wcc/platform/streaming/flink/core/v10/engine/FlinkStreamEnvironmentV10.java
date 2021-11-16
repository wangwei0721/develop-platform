package com.wcc.platform.streaming.flink.core.v10.engine;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.engine.AbsFlinkStreamEnvironment;
import com.wcc.platform.streaming.flink.core.job.FlinkJobConfig;
import com.wcc.platform.streaming.flink.core.side.SideTableInfo;
import com.wcc.platform.streaming.flink.core.sink.SinkTableInfo;
import com.wcc.platform.streaming.flink.core.source.SourceTableInfo;
import com.wcc.platform.streaming.flink.core.sql.SqlTree;
import com.wcc.platform.streaming.flink.core.v10.source.StreamSourceFactoryV10;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class FlinkStreamEnvironmentV10 extends AbsFlinkStreamEnvironment {

    @Override
    protected Table registerSourceTable(SourceTableInfo sourceTableInfo, StreamExecutionEnvironment env, TableEnvironment tableEnv) {
        Object source = new StreamSourceFactoryV10().generateStreamSource(sourceTableInfo, env, tableEnv);
        if (source instanceof Table && tableEnv instanceof StreamTableEnvironment) {
            Table table = (Table) source;
            StreamTableEnvironment streamTableEnv = (StreamTableEnvironment) tableEnv;
            tableEnv.createTemporaryView(sourceTableInfo.getName(), table);

            Table adaptTable = table;
            RowTypeInfo typeInfo = new RowTypeInfo(adaptTable.getSchema().getFieldTypes(),
                    adaptTable.getSchema().getFieldNames());
            DataStream adaptStream = streamTableEnv.toRetractStream(adaptTable, typeInfo)
                    .map((Tuple2<Boolean, Row> f0) -> {
                        return f0.f1;
                    }).returns(typeInfo);

            String fields = String.join(",", typeInfo.getFieldNames());

            Table regTable = streamTableEnv.fromDataStream(adaptStream, fields);
            return regTable;
        }
        throw new PlatformStreamingException("registerSourceTable err");
    }

    @Override
    protected void registerSinkTable(SinkTableInfo sinkTableInfo, TableEnvironment tableEnv) {

    }

    @Override
    public void registerUDF(TableEnvironment tableEnv, SqlTree sqlTree, List<URL> jarURList) {

    }

    @Override
    public void sqlTranslation(FlinkJobConfig config, TableEnvironment tableEnv, SqlTree sqlTree, Map<String, SideTableInfo> sideTables, Map<String, Table> registerTables) {

    }

}
