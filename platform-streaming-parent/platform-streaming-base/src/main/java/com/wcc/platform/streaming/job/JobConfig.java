package com.wcc.platform.streaming.job;

/**
 * 任务通用配置
 *
 * @author wangwei
 * @since 2021-09-10
 */
public abstract class JobConfig {

    protected String jobId;

    protected String name;

    protected String deployMode;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }
}
