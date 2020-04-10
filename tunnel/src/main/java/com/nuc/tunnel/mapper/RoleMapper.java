package com.nuc.tunnel.mapper;

import com.nuc.tunnel.entity.Employee;
import com.nuc.tunnel.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface RoleMapper {

    void deleteByPrimaryKey(Long id);
    //修改角色状态为0（不启用）
    void updateRoleStatusByRoleId(Long id);

    /**
     * 新增角色
     * @param role
     */
    int insert(Role role);

    /**
     * 根据角色id为角色赋予多个用户
     * @param roleId
     * @param userId
     */
    void saveUsersForRole(Long roleId, Long userId);

    /**
     * 根据角色id查询角色信息
     * @param id
     * @return
     */
    Role selectByPrimaryKey(Long id);

    Role selectRoleByRoleName(@Param("roleName") String roleName);

    /**
     * 根据角色id查询角色信息
     * @param roleName
     * @return
     */
    Role selectByRoleIdAndRoleName(String roleName);

    /**
     * 查询所有角色列表
     * @return
     */
    List<Role> selectAll(@Param("roleName") String roleName, @Param("status") String status);

    /**
     * 根据角色id修改角色信息
     * @param role
     */
    void updateByPrimaryKey(Role role);

    /**
     * 根据用户id查询该用户的角色信息
     * @param id
     */
    List<Role> findRoleByEmployeeId(Long id);

    /**
     * 根据角色id查询用户
     * @param roleId
     * @return
     */
    List<Employee> selectUsersByRoleId(Long roleId);
}
