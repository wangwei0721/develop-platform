package com.wcc.platform.streaming.flink.core.source;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;

public interface StreamSourceGener<T> {

    /**
     * 构建source链接
     *
     * @param sourceTableInfo sourceTableInfo
     * @param env             env
     * @param tableEnv        tableEnv
     * @return T
     * @author wangwei
     * @since 2021-11-10
     */
    T genStreamSource(SourceTableInfo sourceTableInfo, StreamExecutionEnvironment env,
                      TableEnvironment tableEnv);
}