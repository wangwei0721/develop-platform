package com.wcc.platform.streaming.job;

import com.wcc.platform.streaming.enums.EngineType;

/**
 * 任务启动命令行传参
 *
 * @author wangwei
 * @since 2021-09-10
 */
public abstract class JobParams {

    protected String[] args;

    public JobParams() {
        JobParamsFactory.jobParams.put(getEngineType().name(), this.getClass());
    }

    /**
     * 将命令行参数转化为JobConfig对象
     *
     * @param args args
     * @return com.wcc.platform.streaming.job.JobConfig
     * @author wangwei
     * @since 2021-09-10
     */
    public JobConfig parseJobConfig(String[] args) {
        this.args = args;
        return parse();
    }

    /**
     * 解析
     *
     * @return com.wcc.platform.streaming.job.JobConfig
     * @author wangwei
     * @since 2021-09-10
     */
    protected abstract JobConfig parse();

    /**
     * 计算引擎类型
     *
     * @return com.wcc.platform.streaming.enums.EngineType
     * @author wangwei
     * @since 2021-09-10
     */
    protected abstract EngineType getEngineType();
}
