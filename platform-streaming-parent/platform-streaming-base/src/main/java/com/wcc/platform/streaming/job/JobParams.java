package com.wcc.platform.streaming.job;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务启动命令行传参
 *
 * @author wangwei
 * @since 2021-09-10
 */
public abstract class JobParams {

    protected static final String JOB_CONF = "jobConfig";
    protected static final String RUN_MODE = "runMode";
    protected static final String ENGINE_TYPE = "engineType";

    protected String[] args;

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


}
