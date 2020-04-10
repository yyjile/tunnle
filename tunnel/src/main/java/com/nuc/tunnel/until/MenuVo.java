package com.nuc.tunnel.until;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class MenuVo implements Serializable{
    private Long id;

    //菜单名称
    private String text;
    private String title;

    //是否可选
    private Boolean isLeaf;

    private Long key;
    //菜单链接
    private String link;
    //    //菜单图标
//    private String icons;
    //菜单图标
    private Icon icon;
    //菜单排序
    private Long orderId;
    //是否显示分组名
    private Boolean group;
    //隐藏面包屑
    private Boolean hideInBreadcrumb;

    private List<MenuVo>  children;
    // 父节点
    private Long parentId;

}
