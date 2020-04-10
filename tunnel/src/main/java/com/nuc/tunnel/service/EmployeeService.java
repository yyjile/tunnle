package com.nuc.tunnel.service;

import com.github.pagehelper.Page;
import com.nuc.tunnel.entity.Employee;
import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.until.PageVo;
import com.nuc.tunnel.until.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface EmployeeService {
    /**
     * 根据用户id删除用户
     * @param id
     * @return
     */
    void deleteByPrimaryKey(Long id);

    /**
     * 根据用户id删除用户
     * @param str
     * @return
     */
    ResultVo deleteByEmployeeIds(String str);

    /**
     * 新增用户
     * @param employee
     */
    ResultVo insert(Employee employee);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    Employee selectByPrimaryKey(Long id);
    Employee selectByUser(String userName);
    PageVo selectByUserName(String userName, String realName, int pageNum, int pageSize);

    /**
     * 根据用户编号，密码查询用户信息
     * @param employee
     * @return
     */
    Employee selectByEmployeeNumAndPassword(Employee employee);

    /**
     * 查询用户列表
     * @return
     */
    PageVo selectAll(Page page);

    /**
     * 根据用户id修改用户
     * @param employee
     */
    ResultVo updateByPrimaryKey(Employee employee);

    ResultVo updateEmployeePass(Long id, String password);

    /**
     * 根据用户id查询该用户的角色信息
     * @param id
     */
    List<Role> findRoleByEmployeeId(Long id);

    PageVo selectUsersByRoleId(Long roleId, Page page);

    /**
     * 保存用户新角色
     * @param employeeId
     * @param roles
     */
    ResultVo saveEmployeeNewRoles(Long employeeId, String roles);


    ResultVo uploadAvatarOrPhoto(@RequestBody Employee employee, MultipartFile avatar, MultipartFile photo) throws IOException;
}
