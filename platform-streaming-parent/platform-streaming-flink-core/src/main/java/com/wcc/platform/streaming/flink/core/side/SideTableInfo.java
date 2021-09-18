package com.wcc.platform.streaming.flink.core.side;

import com.wcc.platform.streaming.flink.core.table.TableInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.RowTypeInfo;

import java.io.Serializable;

/**
 * 目标表
 *
 * @author wangwei
 * @since 2021-09-18
 */
public abstract class SideTableInfo extends TableInfo implements Serializable {

    public static final String TARGET_SUFFIX = "Side";

    public static final String CACHE_KEY = "cache";

    public static final String CACHE_SIZE_KEY = "cacheSize";

    public static final String CACHE_TTLMS_KEY = "cacheTTLMs";

    public static final String PARTITIONED_JOIN_KEY = "partitionedJoin";

    public static final String CACHE_MODE_KEY = "cacheMode";

    public static final String ASYNC_CAP_KEY = "asyncCapacity";

    public static final String ASYNC_TIMEOUT_KEY = "asyncTimeout";

    /**
     * None or LRU or ALL
     */
    private String cacheType = "none";

    private int cacheSize = 10000;

    private long cacheTimeout = 60 * 1000;

    private int asyncCapacity = 100;

    private int asyncTimeout = 10000;

    private boolean partitionedJoin = false;

    private String cacheMode = "ordered";

    public RowTypeInfo getRowTypeInfo() {
        Class[] fieldClass = getFieldClasses();
        TypeInformation<?>[] types = new TypeInformation[fieldClass.length];
        String[] fieldNames = getFields();
        for (int i = 0; i < fieldClass.length; i++) {
            types[i] = TypeInformation.of(fieldClass[i]);
        }

        return new RowTypeInfo(types, fieldNames);
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public long getCacheTimeout() {
        return cacheTimeout;
    }

    public void setCacheTimeout(long cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
    }

    public boolean isPartitionedJoin() {
        return partitionedJoin;
    }

    public void setPartitionedJoin(boolean partitionedJoin) {
        this.partitionedJoin = partitionedJoin;
    }

    public String getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(String cacheMode) {
        this.cacheMode = cacheMode;
    }

    public int getAsyncCapacity() {
        return asyncCapacity;
    }

    public void setAsyncCapacity(int asyncCapacity) {
        this.asyncCapacity = asyncCapacity;
    }

    public int getAsyncTimeout() {
        return asyncTimeout;
    }

    public void setAsyncTimeout(int asyncTimeout) {
        this.asyncTimeout = asyncTimeout;
    }
}
