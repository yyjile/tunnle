package com.nuc.tunnel.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.util.List;


//@Entity
//@Table(name = "mj_menu")
@Data
public class Menu implements Serializable{

    private Long id;
    //菜单编号
    private String menuNo;
    //菜单名称
    private String text;
    //菜单链接
    private String link;
    //菜单描述
    private String menuDesc;
    //菜单图标值
    private String icon;
    //菜单图标类型
    private String iconType;
    //菜单排序
    private Long orderId;
    //是否显示分组名
    private Boolean group;
    //隐藏面包屑
    private Boolean hideInBreadcrumb;
    // 父节点
    private Long parentId;
}
