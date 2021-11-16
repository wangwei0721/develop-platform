package com.wcc.platform.streaming.flink.core.job;

import com.alibaba.fastjson.JSON;
import com.wcc.platform.streaming.enums.DeployMode;
import com.wcc.platform.streaming.exception.PlatformStreamingException;
import com.wcc.platform.streaming.job.JobParams;
import com.wcc.platform.util.FileUtil;

/**
 * flinkSql任务自定义参数
 *
 * @author wangwei
 * @since 2021-10-21
 */
public class FlinkJobParams extends JobParams {

    public FlinkJobParams(String jobConf) {
        super(jobConf);
    }

    @Override
    public FlinkJobConfig parse() {
        try {
            FlinkJobConfig jobConfig = JSON.parseObject(FileUtil.readContent(jobConf), FlinkJobConfig.class);
            super.deployMode = DeployMode.getOrDefault(jobConfig.getDeployMode());
            return jobConfig;
        } catch (Exception e) {
            throw new PlatformStreamingException();
        }
    }


}
