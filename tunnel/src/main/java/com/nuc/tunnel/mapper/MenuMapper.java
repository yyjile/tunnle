package com.nuc.tunnel.mapper;

import com.nuc.tunnel.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {


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
    List<Menu> selectAll();

    /**
     * 根据菜单id修改菜单信息
     * @param menu
     * @return
     */
    void updateByPrimaryKey(Menu menu);

    /**
     * 根据角色id，菜单id修改(复选框选择为新增，不选择为删除)菜单权限
//     * @param roleId
//     * @param menusId
     */
//    void updateRoleMenuByRoleIdAndMenuID(Long roleId,Long menusId);
    void updateRoleMenuByRoleIdAndMenuID(Map<String, Long> map);

    void deleteRoleMenuByRoleIdAndMenuID(Long roleId, Long menusId);


    /**
     * 根据角色id查询角色下的菜单
     * @param roleId
     * @return
     */
    List<Menu> selectMenuByRoleId(Long roleId);

    /**
     * 根据用户id查询角色下的菜单
     * @param userId
     * @return
     */
    List<Menu> selectMenuByUserId(Long userId);


//    void insertRoleMenu(Long roleId,Long menusId);/
    void insertRoleMenu(Map<String, Long> map);

}