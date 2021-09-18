package com.wcc.platform.streaming.flink.core.sql;

/**
 * flinkSql解析
 *
 * @author wangwei
 * @since 2021-09-10
 */
public interface IParser {

    /**
     * 根据正则匹配是否符合该类型解析
     *
     * @param sql sql
     * @return boolean
     * @author wangwei
     * @since 2021-09-10
     */
    boolean verify(String sql);

    /**
     * 逐条解析sql，将最终结果合并到sqlTree对象
     *
     * @param sql     sql
     * @param sqlTree sqlTree
     * @author wangwei
     * @since 2021-09-10
     */
    void parse(String sql, SqlTree sqlTree);
}
