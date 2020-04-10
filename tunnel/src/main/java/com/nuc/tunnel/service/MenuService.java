package com.nuc.tunnel.service;


import com.nuc.tunnel.entity.Menu;
import com.nuc.tunnel.until.MenuVo;
import com.nuc.tunnel.until.ResultVo;

import java.util.List;

public interface MenuService {

    List<MenuVo> findMenuTree(Long roleId);

    /**
     * 根据菜单id删除菜单
     * @param id
     * @return
     */
    void deleteByPrimaryKey(Long id);

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    void insert(Menu menu);

    /**
     * 根据菜单id查询菜单信息
     * @param id
     * @return
     */
    Menu selectByPrimaryKey(Long id);

    /**
     * 查询所有菜单
     * @return
     */
    List<MenuVo> selectAll();

    /**
     * 根据菜单id修改菜单信息
     * @param menu
     * @return
     */
    void updateByPrimaryKey(Menu menu);

    /**
     * 根据角色id，菜单id修改(复选框选择为新增，不选择为删除)菜单权限
     * @param roleId
     * @param menusId
     * @return
     */
    ResultVo updateRoleMenu(Long roleId, String menusId);

    /**
     * 根据用户id查询角色下的菜单
     * @param userId
     * @return
     */
    List<MenuVo> selectMenuByUserId(Long userId);

    void insertRoleMenu(Long roleId, String menusId);
}
