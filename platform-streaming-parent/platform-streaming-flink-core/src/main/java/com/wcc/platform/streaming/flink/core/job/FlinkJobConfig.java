package com.wcc.platform.streaming.flink.core.job;

import com.wcc.platform.streaming.job.JobConfig;

import java.io.Serializable;
import java.util.Properties;

public class FlinkJobConfig extends JobConfig implements Serializable {

    private String engineDriver;
    /**
     * flinkSQL
     */
    private String sql;
    /**
     * flink参数
     */
    private Properties params;

    private String localSqlPlugin;

    private String remoteSqlPlugin;

    private String pluginLoadMode;

    private String version;

    public String getEngineDriver() {
        return engineDriver;
    }

    public void setEngineDriver(String engineDriver) {
        this.engineDriver = engineDriver;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Properties getParams() {
        return params;
    }

    public void setParams(Properties params) {
        this.params = params;
    }

    public String getLocalSqlPlugin() {
        return localSqlPlugin;
    }

    public void setLocalSqlPlugin(String localSqlPlugin) {
        this.localSqlPlugin = localSqlPlugin;
    }

    public String getRemoteSqlPlugin() {
        return remoteSqlPlugin;
    }

    public void setRemoteSqlPlugin(String remoteSqlPlugin) {
        this.remoteSqlPlugin = remoteSqlPlugin;
    }

    public String getPluginLoadMode() {
        return pluginLoadMode;
    }

    public void setPluginLoadMode(String pluginLoadMode) {
        this.pluginLoadMode = pluginLoadMode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "FlinkJobConfig{" +
                "engineDriver='" + engineDriver + '\'' +
                ", sql='" + sql + '\'' +
                ", params=" + params +
                ", localSqlPlugin='" + localSqlPlugin + '\'' +
                ", remoteSqlPlugin='" + remoteSqlPlugin + '\'' +
                ", pluginLoadMode='" + pluginLoadMode + '\'' +
                ", version='" + version + '\'' +
                ", jobId='" + jobId + '\'' +
                ", name='" + name + '\'' +
                ", deployMode='" + deployMode + '\'' +
                '}';
    }
}
