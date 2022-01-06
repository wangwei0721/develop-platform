package com.example.platform.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * zuul动态路由配置;zuul动态路由配置表
 * </p>
 *
 * @author wcc
 * @since 2021-12-31
 */
@TableName("wcc_sys_zuul_route_config")
@ApiModel(value = "ZuulRouteConfig对象", description = "zuul动态路由配置;zuul动态路由配置表")
public class ZuulRouteConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("路由id")
    @TableId(value = "route_id", type = IdType.AUTO)
    private Long routeId;

    @ApiModelProperty("服务标识")
    private String serviceId;

    @ApiModelProperty("服务路由")
    private String servicePath;

    @ApiModelProperty("服务代理地址")
    private String serviceUrl;

    @ApiModelProperty("转发是否去掉前缀")
    private String stripPrefix;

    @ApiModelProperty("是否重试")
    private String retryable;

    @ApiModelProperty("是否配置敏感请求头")
    private String customSensitiveHeaders;

    @ApiModelProperty("启用状态")
    private String statusCd;

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(String stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public String getRetryable() {
        return retryable;
    }

    public void setRetryable(String retryable) {
        this.retryable = retryable;
    }

    public String getCustomSensitiveHeaders() {
        return customSensitiveHeaders;
    }

    public void setCustomSensitiveHeaders(String customSensitiveHeaders) {
        this.customSensitiveHeaders = customSensitiveHeaders;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    public String toString() {
        return "ZuulRouteConfig{" +
                "routeId=" + routeId +
                ", serviceId=" + serviceId +
                ", servicePath=" + servicePath +
                ", serviceUrl=" + serviceUrl +
                ", stripPrefix=" + stripPrefix +
                ", retryable=" + retryable +
                ", customSensitiveHeaders=" + customSensitiveHeaders +
                ", statusCd=" + statusCd +
                "}";
    }
}
