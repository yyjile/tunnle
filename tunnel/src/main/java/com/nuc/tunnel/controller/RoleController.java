package com.nuc.tunnel.controller;


import com.nuc.tunnel.dto.RoleMenuDto;
import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.entity.User;
import com.nuc.tunnel.service.MenuService;
import com.nuc.tunnel.service.RoleService;
import com.nuc.tunnel.until.MenuVo;
import com.nuc.tunnel.until.PageVo;
import com.nuc.tunnel.until.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 根据角色id删除角色
     * 根据角色id修改角色状态为0（不启用）
     * @param id
     */
    @RequestMapping("/deleteRoleById")
    public ResultVo deleteByPrimaryKey(String id) {
        log.info("根据角色id删除角色");
        ResultVo resultVo = roleService.deleteByPrimaryKey(id);
        return resultVo;
    }

    /**
     * 新增角色
     * @param role
     */
    @RequestMapping("/insertRole")
    public ResultVo insert(@RequestBody Role role) {
        log.info("新增角色");
        ResultVo resultVo = roleService.insert(role);
        return resultVo;
    }

    /**
     * 根据角色id查询角色信息
     * @param id
     * @return
     */
    @RequestMapping("/selectRoleById")
    public ResultVo selectByPrimaryKey(Long id) {
        ResultVo resultVo = new ResultVo();
        RoleMenuDto roleMenuDto = new RoleMenuDto();
        log.info("根据角色id查询角色信息");
        Role role = roleService.selectByPrimaryKey(id);
        List<MenuVo> menu = menuService.findMenuTree(role.getId());
        roleMenuDto.setRole(role);
        roleMenuDto.setMenus(menu);
        resultVo.setData(roleMenuDto);
        return resultVo;
    }


    /**
     * 查询所有角色列表
     * @return
     */
    @RequestMapping("/selectAllRole")
    public ResultVo selectAll(String roleName,String status,int pageNum,int pageSize) {
        ResultVo resultVo = new ResultVo();
        log.info("查询所有角色列表");
        PageVo pageVo = roleService.selectAll(roleName,status,pageNum,pageSize);
        resultVo.setData(pageVo);
        return resultVo;
    }

    /**
     * 更新角色信息
     * @param role
     */
    @RequestMapping("/updateRole")
    public ResultVo updateByPrimaryKey(@RequestBody Role role) {
        log.info("修改角色信息");
        ResultVo resultVo = new ResultVo();
        roleService.updateByPrimaryKey(role);
        if (role.getMenus() != null){
            menuService.updateRoleMenu(role.getId(),role.getMenus());
        }
        return resultVo;
    }

    /**
     * 根据角色id为角色赋予多个用户
     * @param roleId 角色id
     * @param strs 用户id集合
     * @return
     */
    @RequestMapping("/saveUsersForRole")
    public ResultVo saveUsersForRole(Long roleId,String strs) {
        log.info("根据角色id为角色赋予多个用户");
        ResultVo resultVo = roleService.saveUsersForRole(roleId,strs);
        return resultVo;
    }

}
