package com.wcc.platform.streaming.engine;

public interface StreamingEngine {

    /**
     * 初始化并设置任务参数
     *
     * @param jobConfig jobConfig
     * @author wangwei
     * @since 2021-09-10
     */
    void init(String jobConfig);

    /**
     * 提交任务启动
     *
     * @author wangwei
     * @since 2021-09-10
     */
    void start();

    /**
     * 停止任务
     *
     * @author wangwei
     * @since 2021-09-10
     */
    void stop();

}