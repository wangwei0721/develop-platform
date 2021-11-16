package com.wcc.platform.streaming.flink.core.v10.source;

import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.flink.core.source.SourceTableInfo;
import com.wcc.platform.streaming.flink.core.source.StreamSourceGener;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

public abstract class AbsStreamSourceGenerV10<T> implements StreamSourceGener<T> {

    @Override
    public T genStreamSource(SourceTableInfo sourceTableInfo, StreamExecutionEnvironment env, TableEnvironment tableEnv) {
        if (tableEnv instanceof StreamTableEnvironment) {
            StreamTableEnvironment streamTableEnv = (StreamTableEnvironment) tableEnv;
            return genStreamSourceV10(sourceTableInfo, env, streamTableEnv);
        }
        throw new PlatformStreamingException("genStreamSource err");
    }

    /**
     * 构建Source链接
     *
     * @param sourceTableInfo sourceTableInfo
     * @param env             env
     * @param tableEnv        tableEnv
     * @return T
     * @author wangwei
     * @since 2021-11-10
     */
    protected abstract T genStreamSourceV10(SourceTableInfo sourceTableInfo, StreamExecutionEnvironment env, StreamTableEnvironment tableEnv);

}