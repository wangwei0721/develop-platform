package com.wcc.platform.streaming.job;

import com.wcc.platform.streaming.enums.DeployMode;

/**
 * 任务启动命令行传入的任务脚本解析为参数对象
 *
 * @author wangwei
 * @since 2021-09-10
 */
public abstract class JobParams {

    protected String jobConf;
    protected DeployMode deployMode;

    public JobParams(String jobConf) {
        this.jobConf = jobConf;
    }

    /**
     * 解析
     *
     * @return com.wcc.platform.streaming.job.JobConfig
     * @author wangwei
     * @since 2021-09-10
     */
    public abstract JobConfig parse();

    public DeployMode getDeployMode() {
        return deployMode;
    }
}
