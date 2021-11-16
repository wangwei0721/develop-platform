package com.wcc.platform.streaming.flink.core.engine;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.job.FlinkJobConfig;
import com.wcc.platform.streaming.flink.core.side.SideTableInfo;
import com.wcc.platform.streaming.flink.core.sink.SinkTableInfo;
import com.wcc.platform.streaming.flink.core.source.SourceTableInfo;
import com.wcc.platform.streaming.flink.core.sql.SqlTree;
import com.wcc.platform.streaming.flink.core.table.TableInfo;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;

import java.util.Map;

public abstract class AbsFlinkStreamEnvironment implements FlinkStreamEnvironment {

    @Override
    public void registerTable(FlinkJobConfig config, StreamExecutionEnvironment env, TableEnvironment tableEnv, SqlTree sqlTree,
                              Map<String, SideTableInfo> sideTables, Map<String, Table> registerTables) {
        for (TableInfo tableInfo : sqlTree.getTableInfoMap().values()) {
            if (tableInfo instanceof SourceTableInfo) {
                SourceTableInfo sourceTableInfo = (SourceTableInfo) tableInfo;
                Table table = registerSourceTable(sourceTableInfo, env, tableEnv);
                registerTables.put(tableInfo.getName(), table);
            } else if (tableInfo instanceof SinkTableInfo) {
                SinkTableInfo sinkTableInfo = (SinkTableInfo) tableInfo;
                registerSinkTable(sinkTableInfo, tableEnv);
            } else if (tableInfo instanceof SideTableInfo) {
                SideTableInfo sideTableInfo = (SideTableInfo) tableInfo;
                sideTables.put(sideTableInfo.getName(), sideTableInfo);
            } else {
                throw new PlatformStreamingException("not support table type:" + tableInfo.getType());
            }
        }
    }

    /**
     * 注册源表
     *
     * @param sourceTableInfo sourceTableInfo
     * @param env             env
     * @param tableEnv        tableEnv
     * @return org.apache.flink.table.api.Table
     * @author wangwei
     * @since 2021-11-10
     */
    protected abstract Table registerSourceTable(SourceTableInfo sourceTableInfo, StreamExecutionEnvironment env, TableEnvironment tableEnv);

    /**
     * 注册目标表
     *
     * @param sinkTableInfo sinkTableInfo
     * @param tableEnv      tableEnv
     * @author wangwei
     * @since 2021-11-10
     */
    protected abstract void registerSinkTable(SinkTableInfo sinkTableInfo, TableEnvironment tableEnv);


}
