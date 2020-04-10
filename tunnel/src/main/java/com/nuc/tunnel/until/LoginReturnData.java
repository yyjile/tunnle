package com.nuc.tunnel.until;

import com.nuc.tunnel.entity.Employee;
import lombok.Data;

import java.util.List;

/**
 * 登录返回数据
 */
@Data
public class LoginReturnData {
    private App app;

    private List<MenuVo> menu;

    private String token;

    private Employee user;
}
