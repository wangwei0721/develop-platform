package com.wcc.platform.exception;

import java.io.Serializable;
import java.util.Date;

/**
 * 异常信息
 *
 * @author wangwei
 * @since 2021-09-10
 */
public final class PlatformException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int INNER_ERROR = 1;
    public static final int BIZ_ERROR = 0;

    private int id;
    private String code;
    private String desc;
    private String localeMessage;
    private Date time;
    private int type;
    private Exception exception;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocaleMessage() {
        return localeMessage;
    }

    public void setLocaleMessage(String localeMessage) {
        this.localeMessage = localeMessage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}