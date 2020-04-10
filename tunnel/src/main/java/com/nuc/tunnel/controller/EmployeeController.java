package com.nuc.tunnel.controller;

import com.github.pagehelper.Page;
import com.nuc.tunnel.entity.Employee;
import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.entity.User;
import com.nuc.tunnel.service.EmployeeService;
import com.nuc.tunnel.service.MenuService;
import com.nuc.tunnel.until.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MenuService menuService;

    /**
     * 根据用户id删除用户
     * @param id
     * @return
     */
    @RequestMapping("/deleteEmployee")
    public void deleteByPrimaryKey(Long id) {
        log.info("调用删除用户接口");
        employeeService.deleteByPrimaryKey(id);
    }

    /**
     * 根据用户id批量删除用户
     * 只修改用户状态值 为0 不启用
     * @param id
     * @return
     */
    @RequestMapping("/deleteByEmployeeIds")
    public ResultVo deleteByEmployeeIds(String id) {
        ResultVo resultVo =new ResultVo();
        log.info("调用删除用户接口");
        if (StringUtils.isBlank(id)){
            resultVo.setMsg("勾选不能为空");
            resultVo.setStatus(400);
            return resultVo;
        }
        resultVo =  employeeService.deleteByEmployeeIds(id);
        return resultVo;
    }

    /**
     * 添加新用户
     * @return
     */
    @RequestMapping("/insertEmployee")
    public ResultVo insertEmployee(@RequestBody Employee employee){
        ResultVo resultVo = new ResultVo();
        log.info("添加新用户接口");
        Employee userName = employeeService.selectByUser(employee.getUserName());
        if (userName != null){//如果不为空
            resultVo.setMsg("该用户名已存在！");
            resultVo.setStatus(400);
            return resultVo;
        }
        employeeService.insert(employee);
        return resultVo;
    }

    /**
     *根据用户id查询用户信息
     * @param id
     * @return
     */
    @RequestMapping("/selectEmployeeById")
    public ResultVo selectByPrimaryKey(Long id) {
        ResultVo resultVo = new ResultVo();
        Employee employee = employeeService.selectByPrimaryKey(id);
        resultVo.setData(employee);
        log.info("查询用户信息接口");
        return resultVo;
    }

    /**
     *根据用户名查询用户信息
     * @param userName
     * @return
     */
    @RequestMapping("/selectEmployeeByUserName")
    public ResultVo selectEmployeeByUserName(String userName,String realName,int pageNum,int pageSize) {
        ResultVo resultVo = new ResultVo();
        if (userName != null || realName != null){
            PageVo pageVo = employeeService.selectByUserName(userName,realName,pageNum,pageSize);
            log.info("查询用户信息接口");
            if (pageVo == null || pageVo.getTotalElement() == 0){
                resultVo.setMsg("查询用户不存在");
                resultVo.setStatus(400);
                return resultVo;
            }
            resultVo.setData(pageVo);
            return resultVo;
        }
        resultVo.setMsg("输入不可为空");
        return resultVo;
    }

    /**
     * 查询所有用户
     * @return
     */
    @RequestMapping("/getAllEmployee")
    public ResultVo selectAll(String userName,String realName,int pageNum,int pageSize){
        ResultVo resultVo = new ResultVo();
        //如果参数不为空，模糊查询分页
        if (userName !=null || realName !=null){
            log.info("模糊查询用户接口");
            PageVo pageVo = employeeService.selectByUserName(userName,realName,pageNum,pageSize);
            if (pageVo == null || pageVo.getTotalElement() == 0){
                resultVo.setMsg("查询用户不存在");
                resultVo.setStatus(400);
                return resultVo;
            }
            resultVo.setData(pageVo);
            return resultVo;
        }else {
            //如果参数为空，查询所有分页
            Page page = new Page(pageNum,pageSize);
            PageVo pageVo = employeeService.selectAll(page);
            resultVo.setData(pageVo);
            log.info("查询所有用户接口");
            return resultVo;
        }
    }

    /**
     * 根据用户id更新用户
     * @param employee
     */
    @RequestMapping("/updateEmployeeById")
    public ResultVo updateByPrimaryKey(@RequestBody Employee employee) {
        ResultVo resultVo = new ResultVo();
        log.info("更新用户接口");
        employeeService.updateByPrimaryKey(employee);
        return resultVo;
    }

    /**
     * 修改用户密码
     * @param id
     * @param password
     */
    @RequestMapping("/updateEmployeePass")
    public ResultVo updateEmployeePass(Long id,String password) {
        ResultVo resultVo = employeeService.updateEmployeePass(id,password);
        log.info("修改用户密码接口");
        return resultVo;
    }

    /**
     * 根据用户id查询该用户的角色信息
     * @param id
     */
    @RequestMapping("/findRoleByEmployeeId")
    public ResultVo findRoleByEmployeeId(Long id) {
        ResultVo resultVo = new ResultVo();
        List<Role> roles = employeeService.findRoleByEmployeeId(id);
        resultVo.setData(roles);
        log.info("添查询该用户的角色信息接口");
        return resultVo;
    }


    /**
     * 保存用户新角色
     * @param employeeId
     * @param roles
     * @return
     */
    @RequestMapping("/saveEmployeeNewRoles")
        public ResultVo saveEmployeeNewRoles(Long employeeId,String roles){
        ResultVo resultVo = new ResultVo();
        resultVo = employeeService.saveEmployeeNewRoles(employeeId,roles);
        return  resultVo;
    }

    /**
     * 根据角色查询用户
     * @param roleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectUsersByRoleId")
    public ResultVo selectUsersByRoleId(Long roleId,int pageNum,int pageSize) {
        ResultVo resultVo =new ResultVo();
        Page page = new Page(pageNum,pageSize);
        PageVo pageVo = employeeService.selectUsersByRoleId(roleId,page);
        resultVo.setData(pageVo);
        return resultVo;
    }

    /**
     * 用户登录
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login1")
    public ResultVo login2(String userName, String password, HttpServletRequest request, HttpServletResponse response){
        ResultVo resultVo = new ResultVo();
        Employee employee = new Employee();
        employee.setUserName(userName);
        employee.setPassword(password);
        log.info("d登录接口");
        //通过用户名密码查询用户
        Employee employees = employeeService.selectByEmployeeNumAndPassword(employee);
        if (employees !=null){
            request.getSession().setAttribute(EmployeeContext.USERSESSION,employees);
            //根据用户查询对应菜单树列表
//            List<MenuVo> menuVoList = menuService.selectMenuByUserId(employees.getId());
//            request.getSession().setAttribute(EmployeeContext.MENUSESSION,menuVoList);
            EmployeeContext.setRequest(request);
//            resultVo.setData(menuVoList);
            resultVo.setMsg("用户名和密码正确，登录成功!!!");
        }else {
            resultVo.setMsg("用户名和密码不正确，请重新输入！");
        }
        return  resultVo;
    }

    /**
     * 用户登录
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public ResultVo login( Employee employee, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultVo resultVo = new ResultVo();
        log.info("登录接口");
        //通过用户名密码查询用户
        Employee employees = employeeService.selectByEmployeeNumAndPassword(employee);
        if (employees !=null){
            if (!employees.getState().equals("0")){
                //根据用户查询对应菜单树列表
                List<MenuVo> menuVoList = menuService.selectMenuByUserId(employees.getId());
                LoginReturnData loginReturnData = new LoginReturnData();
                App app = new App();
                loginReturnData.setApp(app);
                loginReturnData.setUser(employees);
                loginReturnData.setMenu(menuVoList);
                resultVo.setData(employees);
                resultVo.setMsg("用户名和密码正确，登录成功!!!");
                response.sendRedirect("http://localhost:8080/welcome.html");
            }else {
                resultVo.setMsg("该用户已被停用，请联系管理员！");
                resultVo.setStatus(401);
            }
        }else {
            resultVo.setMsg("用户名和密码不正确，请重新输入！");
            resultVo.setStatus(401);
        }
        return  resultVo;
    }

    //备用
    @RequestMapping("/login2")
    public ResultVo login2(@RequestBody Employee employee, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultVo resultVo = new ResultVo();
        //通过用户名密码查询用户
        String userName = employee.getUserName();
        String password = employee.getPassword();
        Employee employees = employeeService.selectByEmployeeNumAndPassword(employee);
        log.info("employees"+employee);
        if (employees !=null){
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, MD5.encode(userName+password+"mj专用加密算法"));
            System.out.println(token);
            try {
                subject.login(token);
                resultVo.setData(subject.getSession().getId());
                resultVo.setMsg("用户名和密码正确，登录成功!!!");
                response.sendRedirect("http://localhost:8080/welcome.html");
            }catch (AuthenticationException e){
                resultVo.setData(e.getMessage());
                resultVo.setStatus(401);
                return resultVo;
            }
        }else {
            resultVo.setMsg("用户名和密码不正确，请重新输入！");
            resultVo.setStatus(401);
        }
        return  resultVo;
    }

    /**
     * 用户登出
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logOut11")
    public ResultVo logOut11(HttpServletRequest request, HttpServletResponse response){
        ResultVo resultVo = new ResultVo();
        Subject subject = SecurityUtils.getSubject();
        if (subject != null){
            subject.logout();
        }
        resultVo.setMsg("退出登录！");
        return  resultVo;
    }

/*

    */
/**
     * 员工批量导入接口
     *//*

    @RequestMapping("/importEmployee")
    public ResultVo importEmployee(MultipartFile importFile) throws Exception {
        ResultVo resultVo = new ResultVo();
        if (!importFile.getOriginalFilename().contains("xls") && !importFile.getOriginalFilename().contains("xlsx")){
            resultVo.setStatus(400);
            resultVo.setMsg("您选择的文件类型错误，请选择Excel文件进行上传！");
            return resultVo;
        }
        InputStream inputStream = importFile.getInputStream();
        List<Employee> employeeList = ImportExcel.getBankListByExcel(inputStream);
        if (employeeList.size() > 0){
            for (Employee employee : employeeList){
                employeeService.insert(employee);
            }
            resultVo.setMsg("导入成功！");
            inputStream.close();
            return resultVo;
        }else {
            resultVo.setMsg("导入失败！");
            resultVo.setStatus(400);
            return resultVo;
        }
    }
*/


    /**
     * 用户上传头像,照片
     * @param employee
     * @param avatar
     * @return
     */
    @RequestMapping("/uploadAvatarOrPhoto")
    public ResultVo uploadAvatarOrPhoto(@RequestBody Employee employee, MultipartFile avatar, MultipartFile photo) throws IOException {
        ResultVo resultVo = new ResultVo();
        employeeService.uploadAvatarOrPhoto(employee,avatar,photo);
        return resultVo;
    }
    /**
     * 用户登出
     * @param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/loginOut22")
    public ResultVo logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultVo resultVo = new ResultVo();
        Subject subject = SecurityUtils.getSubject();
        if (subject != null){
            subject.logout();
        }
        response.sendRedirect("http://localhost:8080/index.html");
        resultVo.setMsg("退出登录！");
        return  resultVo;
    }

    /**
     * 用户登出
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("loginOut")
    public ResultVo loginOut(HttpServletResponse response) throws IOException {
        ResultVo resultVo = new ResultVo();
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
            //System.out.println("退出");
        }
        response.sendRedirect("http://localhost:8080/index.html");
        resultVo.setMsg("退出登录！");
        return resultVo;
    }
}
