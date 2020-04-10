package com.nuc.tunnel.dto;

import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.until.MenuVo;
import lombok.Data;

import java.util.List;


/**
 * Created by: NanLeiLei
 * Company: MJ
 * Date: 2019/03/13
 * Time: 15:16
 */
@Data
public class RoleMenuDto {
    private Role role;
    private List<MenuVo> menus;
}
