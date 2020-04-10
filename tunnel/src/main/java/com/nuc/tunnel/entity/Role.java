package com.nuc.tunnel.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class Role implements Serializable{

    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 角色状态
     */
    private String status;
    /**
     * 备注
     */
    private String remarks;

    //菜单集合
    private String menus;
}

