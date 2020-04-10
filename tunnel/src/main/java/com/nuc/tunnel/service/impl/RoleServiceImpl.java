package com.nuc.tunnel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.mapper.MenuMapper;
import com.nuc.tunnel.mapper.RoleMapper;
import com.nuc.tunnel.service.MenuService;
import com.nuc.tunnel.service.RoleService;
import com.nuc.tunnel.until.PageVo;
import com.nuc.tunnel.until.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: NanLeiLei
 * Company: MJ
 * Date: 2018/11/28
 * Time: 15:14
 */
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuService menuService;

    /**
     * 根据角色id修改角色状态为0（不启用）
     * @param strs
     */
    @Override
    public ResultVo deleteByPrimaryKey(String strs) {
        ResultVo resultVo = new ResultVo();
        String[] strings = strs.split(",");
        if (strings.length ==0){
            resultVo.setMsg("角色Id不能为空！");
            return resultVo;
        }
        for (String s:strings){
            Long id =Long.parseLong(s);
            roleMapper.updateRoleStatusByRoleId(id);
        }
        return resultVo;
    }

    /**
     * 新增角色
     * @param role
     */
    @Override
    public ResultVo insert(Role role) {
        ResultVo resultVo = new ResultVo();
        Map<String,Object> map = new HashMap<>();
        Role roles = roleMapper.selectByRoleIdAndRoleName(role.getRoleName());
        if (roles == null){
            roleMapper.insert(role);
        }else {
            resultVo.setMsg("该角色已存在！");
            resultVo.setStatus(400);
            return resultVo;
        }
        Role newRole = roleMapper.selectRoleByRoleName(role.getRoleName());
        if (newRole != null){
            if (role.getMenus() !=null){
                String[] strings = role.getMenus().split(",");
                // 为角色赋予权限
                if (strings.length !=0){
                    for (String s : strings){
                        Long roleId = newRole.getId();
                        Long menusId = Long.parseLong(s);
                        Map<String,Long> map1 = new HashMap<>();
                        map1.put("roleId",roleId);
                        map1.put("menusId",menusId);
                        menuMapper.insertRoleMenu(map1);
                    }
                }
                resultVo.setMsg("角色创建成功！");
                return resultVo;

            }else {
                resultVo.setMsg("传入菜单id为空");
                resultVo.setStatus(400);
                return resultVo;
            }
        }else {
            resultVo.setMsg("新增角色失败");
            resultVo.setStatus(400);
            return resultVo;
        }

    }

    /**
     * 根据角色id为角色赋予多个用户
     * @param roleId
     * @param strs
     * @return
     */
    @Override
    public ResultVo saveUsersForRole(Long roleId, String strs) {
        ResultVo resultVo = new ResultVo();
        String[] strings = strs.split(",");
        if (strings.length == 0){
            resultVo.setMsg("所选用户为空!");
            resultVo.setStatus(401);
            return resultVo;
        }else {
            for (String s : strings){
                roleMapper.saveUsersForRole(roleId,Long.parseLong(s));
            }
            resultVo.setMsg("保存成功！");
            return resultVo;
        }
    }

    /**
     * 根据角色id查询角色信息
     * @param id
     * @return
     */
    @Override
    public Role selectByPrimaryKey(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Role selectRoleByRoleName(String roleName) {
        return roleMapper.selectRoleByRoleName(roleName);
    }

    /**
     * 查询所有角色列表
     * @return
     */
    @Override
    public PageVo selectAll(String roleName, String status, int pageNum, int pageSize) {
        PageVo pageVo = new PageVo();
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roles = roleMapper.selectAll(roleName,status);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
        pageVo = pageVo.getPageVo(roles,pageInfo);
        return pageVo;
    }

    /**
     * 根据角色id更新角色信息
     * @param role
     */
    @Override
    public ResultVo updateByPrimaryKey(Role role) {
        ResultVo resultVo = new ResultVo();
        Role roles = roleMapper.selectByPrimaryKey(role.getId());
        if (roles != null){
            roleMapper.updateByPrimaryKey(role);
            resultVo.setMsg("修改成功！");
        }else {
            resultVo.setMsg("该角色不存在！");
        }
        return resultVo;
    }

    @Override
    public Role selectByRoleIdAndRoleName(Long id, String roleName) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("roleName",roleName);
//        return roleMapper.selectByRoleIdAndRoleName(map);
        return null;
    }


}
