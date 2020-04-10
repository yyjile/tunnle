package com.nuc.tunnel.until;


import java.io.Serializable;

/**
 * @Author: admin
 * @Description:
 * @Date: Created in 14:15 2018/4/10
 * @Modified By:
 */
public class ExcelBean implements Serializable{
    /**
     * 标题名
     */
    private String headTextName;
    /**
     * 字段名
     */
    private String propertyName;

    public ExcelBean(String headTextName, String propertyName) {
        this.headTextName = headTextName;
        this.propertyName = propertyName;
    }

    public String getHeadTextName() {
        return headTextName;
    }

    public void setHeadTextName(String headTextName) {
        this.headTextName = headTextName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}
