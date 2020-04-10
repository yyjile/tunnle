package com.nuc.tunnel.mapper;

import com.nuc.tunnel.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {
    /**
     * 根据用户id删除用户
     * @param id
     * @return
     */
    void deleteByPrimaryKey(Long id);
    /**
     * 根据用户id修改用户状态为0
     * @param id
     * @return
     */
    void deleteUserById(Long id);

    /**
     * 新增用户
     * @param employee
     */
    void insert(Employee employee);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    Employee selectByPrimaryKey(Long id);

    Employee selectByUser(@Param("userName") String userName);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    List<Employee> selectByUserName(@Param("userName") String userName, @Param("realName") String realName);


    /**
     * 查询用户列表
     * @return
     */
    List<Employee> selectAll();

    /**
     * 根据用户id修改用户
     * @param employee
     */
    int updateByPrimaryKey(Employee employee);

    void updateEmployeePass(Map<String, Object> map);


    /**
     * 保存用户新角色
     * @param employeeId
     * @param roleId
     */
    void saveEmployeeNewRoles(Long employeeId, Long roleId);

    /**
     * 根据用户编号，密码查询用户信息
     * @param map
     * @return
     */
    Employee selectByEmployeeNumAndPassword(Map<String, String> map);

    /**
     * 根据角色id查询用户
     * @param roleId
     * @return
     */
    List<Employee> selectUsersByRoleId(Long roleId);
}