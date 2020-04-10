package com.nuc.tunnel.until;

/**
 * @Author: admin
 * @Description: 分页排序类
 * @Date: Created in 13:51 2018/3/29
 * @Modified By:
 */
public class Sort {

    /**
     * 排序方式
     */
    private String direction;

    /**
     * 根据这个元素排序
     */
    private String property;

    /**
     * 是否忽略大小写
     */
    private Boolean ignoreCase;

    private String nullHandling;

    private Boolean ascending;//升序

    /**
     * 降序
     */
    private Boolean descending;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getNullHandling() {
        return nullHandling;
    }

    public void setNullHandling(String nullHandling) {
        this.nullHandling = nullHandling;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }
}
