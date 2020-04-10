package com.nuc.tunnel.service;

import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.entity.User;
import com.nuc.tunnel.until.PageVo;
import com.nuc.tunnel.until.ResultVo;

import java.util.List;


public interface RoleService {

    /**
     * 根据角色id删除角色
     * @param strs
     */
    ResultVo deleteByPrimaryKey(String strs);

    /**
     * 新增角色
     * @param role
     */
    ResultVo insert(Role role);

    /**
     * 根据角色id为角色赋予多个用户
     * @param roleId
     * @param strs
     * @return
     */
    ResultVo saveUsersForRole(Long roleId,String strs);

    /**
     * 根据角色id查询角色信息
     * @param id
     * @return
     */
    Role selectByPrimaryKey(Long id);
    Role selectRoleByRoleName(String roleName);

    /**
     * 查询所有角色列表
     * @return
     */
    PageVo selectAll(String roleName,String status,int pageNum,int pageSize);
    /**
     * 根据角色id修改角色信息
     * @param role
     */
    ResultVo updateByPrimaryKey(Role role);

    /**
     * 根据角色id,角色名称查询角色信息
     * @param id
     * @param roleName
     * @return
     */
    Role selectByRoleIdAndRoleName(Long id,String roleName);

}
