package com.nuc.tunnel.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户编码
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     *  最后一次登录时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date lastLogin;

    /**
     * 备注
     */
    private String remarks;

    private List<Role> roles;

    private Boolean checkStatus;//是否选中

    public User(User user){
        this.id = user.getId();
        this.userNo = user.getUserNo();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.lastLogin = user.getLastLogin();
        this.remarks = user.getRemarks();
        this.roles = user.getRoles();
        this.checkStatus = user.getCheckStatus();
    }
}
