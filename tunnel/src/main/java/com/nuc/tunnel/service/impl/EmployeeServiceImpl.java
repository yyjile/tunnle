package com.nuc.tunnel.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuc.tunnel.entity.Employee;
import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.mapper.EmployeeMapper;
import com.nuc.tunnel.mapper.RoleMapper;
import com.nuc.tunnel.service.EmployeeService;
import com.nuc.tunnel.until.PageVo;
import com.nuc.tunnel.until.PassMatcher;
import com.nuc.tunnel.until.ResultVo;
import com.yutong.core.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: NanLeiLei
 * Company: MJ
 * Date: 2018/11/23
 * Time: 20:02
 */

@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void deleteByPrimaryKey(Long id) {
        ResultVo resultVo = new ResultVo();
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResultVo deleteByEmployeeIds(String str) {
        ResultVo resultVo = new ResultVo();
        String[] strings = str.split(",");
        if (strings.length == 0){
            resultVo.setMsg("删除失败!");
            resultVo.setStatus(400);
            return resultVo;
        }
        for (String s:strings){
            Long userId = Long.parseLong(s);
            employeeMapper.deleteUserById(userId);
        }
        resultVo.setMsg("删除成功");
        return resultVo;
    }

    @Override
    public ResultVo insert(Employee employee) {
        ResultVo resultVo = new ResultVo();
        Employee employees = employeeMapper.selectByPrimaryKey(employee.getId());
        if (employees == null){
            String password = PassMatcher.getPass(employee.getUserName(),employee.getPassword());
            //初始密码为用户名和密码加密
            employee.setPassword(password);
            //创建日期
            employee.setInputTime(new Date());
            employeeMapper.insert(employee);
            resultVo.setMsg("成功");
        }else {
            resultVo.setMsg("失败!");
            resultVo.setMsg("该用户："+employee.getUserName()+"已存在!");
        }
        return resultVo;
    }

    @Override
    public Employee selectByPrimaryKey(Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if (employee != null){
            //传给前端显示隐藏密码
            employee.setPassword("******");
        }
        return employee;
    }

    @Override
    public Employee selectByUser(String userName) {
        return employeeMapper.selectByUser(userName);
    }

    @Override
    public PageVo selectByUserName(String userName, String realName, int pageNum, int pageSize) {
        PageVo pageVo = new PageVo();
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> employeeList = employeeMapper.selectByUserName(userName,realName);
        if (employeeList != null && employeeList.size()>0){
            for (Employee employee1 : employeeList){
                //传给前端显示隐藏密码
                employee1.setPassword("******");
            }
        }
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        pageVo = pageVo.getPageVo(employeeList,pageInfo);
        return pageVo;
    }

    @Override
    public Employee selectByEmployeeNumAndPassword(Employee employee) {
        String userName = employee.getUserName();
        String password = PassMatcher.getPass(employee.getUserName(),employee.getPassword());
        Map<String,String> map = new HashMap<>();
        map.put("userName",userName);
        map.put("password",password);
        return employeeMapper.selectByEmployeeNumAndPassword(map);
    }

    @Override
    public PageVo selectAll(Page page) {
        PageVo pageVo = new PageVo();
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Employee> employeeList =  employeeMapper.selectAll();
        for (Employee employee : employeeList){
            if (employee != null){
                //传给前端显示隐藏密码
                employee.setPassword("******");
            }
        }
        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);
        pageVo = pageVo.getPageVo(employeeList,pageInfo);
        return pageVo;
    }

    @Override
    public ResultVo updateByPrimaryKey(@RequestBody Employee employee) {
        ResultVo resultVo = new ResultVo();
//        System.out.println(employee.getId());
        employeeMapper.updateByPrimaryKey(employee);
        resultVo.setMsg("更新成功！");
        return resultVo;
    }

    @Override
    public ResultVo updateEmployeePass(Long id, String password) {
        ResultVo resultVo = new ResultVo();
        Map<String,Object> map = new HashMap<>();
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        String newPass = PassMatcher.getPass(employee.getUserName(),password);
        map.put("id",id);
        map.put("password",newPass);
        employeeMapper.updateEmployeePass(map);
        return resultVo;
    }

    @Override
    public List<Role> findRoleByEmployeeId(Long id) {
        return roleMapper.findRoleByEmployeeId(id);
    }

    @Override
    public PageVo selectUsersByRoleId(Long roleId,Page page) {
        PageVo pageVo = new PageVo();
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Employee> employeeList = employeeMapper.selectUsersByRoleId(roleId);
        PageInfo<Employee> pageInfo = new PageInfo<Employee>(employeeList);
        pageVo = pageVo.getPageVo(employeeList,pageInfo);
        return pageVo;
    }

    @Override
    public ResultVo saveEmployeeNewRoles(Long employeeId, String roles) {
        ResultVo resultVo = new ResultVo();
        String[] strings = roles.split(",");
        if (strings.length == 0){
            resultVo.setData("failed!");
            resultVo.setMsg("请求失败!");
            resultVo.setStatus(400);
        }
        for (String s:strings){
            Long roleId = Long.parseLong(s);
            employeeMapper.saveEmployeeNewRoles(employeeId,roleId);
        }
        return resultVo;
    }
    @Override
    public ResultVo uploadAvatarOrPhoto(@RequestBody Employee employee, MultipartFile avatar, MultipartFile photo) throws IOException {
        ResultVo resultVo = new ResultVo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            if (!avatar.isEmpty()){
                String fileName = employee.getUserName()+employee.getRealName()+sdf.format(new Date())+".jpg";
                String realPath = "E:MJ/empAvatar/";
               // FileUtils.copyInputStreamToFile(avatar.getInputStream(),new File(realPath,fileName));
                employee.setAvatar("/empAvatar/"+fileName);
                resultVo.setMsg("上传头像");
            }
            if (!photo.isEmpty()){
                String fileName = employee.getUserName()+employee.getRealName()+sdf.format(new Date())+".jpg";
                String realPath = "E:MJ/photo/";
              //  FileUtils.copyInputStreamToFile(avatar.getInputStream(),new File(realPath,fileName));
                employee.setAvatar("/photo/"+fileName);
                resultVo.setMsg("上传照片");
            }
        }finally {
            if (!avatar.isEmpty()){
                avatar.getInputStream().close();
            }
            if (!photo.isEmpty()){
                photo.getInputStream().close();
            }
        }
        employeeMapper.updateByPrimaryKey(employee);
        return resultVo;
    }
}
