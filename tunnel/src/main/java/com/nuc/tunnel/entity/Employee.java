package com.nuc.tunnel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工表
 */
@Data
@ToString
public class Employee implements Serializable{
    private Long id;
    /**
     * 用户名--工号--登录账号
     */
    private String userName;
    /**
     * 员工编号
     */
    private String userNo;
    /**
     * 员工名称
     */
    private String realName;
    /**
     * 员工密码
     */
    private String password;
    /**
     * 员工电话
     */
    private String tel;
    /**
     * 员工邮箱
     */
    private String email;
    /**
     * 员工录入时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date inputTime;
    /**
     * 员工状态--判断状态
     */
    private String state;

    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 条码
     */
    private String code;
    /**
     * 部门
     */
    private String department;

    private String photo;//照片


}