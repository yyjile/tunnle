package com.nuc.tunnel.controller;

import com.nuc.tunnel.entity.Employee;
import com.nuc.tunnel.entity.Menu;
import com.nuc.tunnel.service.EmployeeService;
import com.nuc.tunnel.service.MenuService;
import com.nuc.tunnel.until.App;
import com.nuc.tunnel.until.LoginReturnData;
import com.nuc.tunnel.until.MenuVo;
import com.nuc.tunnel.until.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private EmployeeService employeeService;


    /**
     * 根据角色id查询菜单--传出当前角色菜单树
     * @param roleId
     * @return
     */
    @RequestMapping("/findRoleMenus")
    @ResponseBody
    public ResultVo findRoleMenus(Long roleId) {
        ResultVo resultVo = new ResultVo();
        log.info("传出当前角色菜单树");
        List<MenuVo> menuVos = menuService.findMenuTree(roleId);
        resultVo.setData(menuVos);
        return resultVo;
    }

    /**
     * 根据用户id查询菜单--传出当前角色菜单树
     * @param userId
     * @return
     */
    @RequestMapping("/selectMenuByUserId")
    @ResponseBody
    public ResultVo selectMenuByUserId(String userId) {
        ResultVo resultVo = new ResultVo();
        LoginReturnData loginReturnData = new LoginReturnData();
        log.info("加载用户菜单接口");
        if (StringUtils.isBlank(userId)){
            loginReturnData.setApp(null);
            loginReturnData.setMenu(null);
            loginReturnData.setUser(null);
            resultVo.setData(loginReturnData);
            resultVo.setMsg("请求参数不能为空，获取菜单失败！");
            return resultVo;
        }else {
            Employee employee = employeeService.selectByPrimaryKey(Long.parseLong(userId));
            if (employee == null){
                resultVo.setMsg("该用户不存在，获取菜单失败！");
                return resultVo;
            }else {
                List<MenuVo> menuVos = menuService.selectMenuByUserId(Long.parseLong(userId));
                App app = new App();
                loginReturnData.setApp(app);
                loginReturnData.setMenu(menuVos);
                loginReturnData.setUser(employee);
                resultVo.setData(loginReturnData);
                resultVo.setMsg("获取菜单成功！");
                return resultVo;
            }
        }
    }

    /**
     * 根据菜单id删除菜单
     * @param id
     * @return
     */
    @RequestMapping("/deleteMenuByPrimaryKey")
    public void deleteByPrimaryKey(Long id) {
        log.info("根据菜单id删除菜单");
        menuService.deleteByPrimaryKey(id);
    }

    /**
     * 新增菜单
     * @param menus
     * @return
     */
    @RequestMapping("/insertMenu")
    public void insert(Menu menus) {
        log.info("新增菜单");
        menuService.insert(menus);
    }

    /**
     * 根据菜单id查询菜单信息
     * @param id
     * @return
     */
    @RequestMapping("/selectMenuByPrimaryKey")
    public ResultVo selectByPrimaryKey(Long id) {
        ResultVo resultVo = new ResultVo();
        log.info("根据菜单id查询菜单信息");
        Menu  menu =  menuService.selectByPrimaryKey(id);
        resultVo.setData(menu);
        return resultVo;
    }

    /**
     * 查询所有菜单--新增角色添加权限加载所有菜单
     * @return resultVo
     */
    @RequestMapping("/selectAllMenu")
    public ResultVo selectAll() {
        ResultVo resultVo = new ResultVo();
        log.info("查询所有菜单");
        List<MenuVo> menuList =  menuService.selectAll();
        resultVo.setData(menuList);
        return resultVo;
    }

    /**
     * 根据菜单id修改菜单信息
     * @param menus
     * @return
     */
    @RequestMapping("/updateMenuByPrimaryKey")
    public void updateByPrimaryKey(Menu menus) {
        log.info("修改菜单信息");
        menuService.updateByPrimaryKey(menus);
    }

    /**
     * 根据角色id，菜单id更新(复选框选择为新增，不选择为删除)菜单权限
     * @param roleId
     * @param menusId
     * @return
     */
    @RequestMapping("/updateRoleMenu")
    public ResultVo updateRoleMenu(Long roleId,String menusId) {
        ResultVo resultVo = new ResultVo();
        log.info("更新角色菜单权限");
        menuService.updateRoleMenu(roleId,menusId);
        return resultVo;
    }

    /**
     * 新增角色为角色赋予菜单权限
     * @param roleId
     * @param menusId
     * @return
     */
    @RequestMapping("/")
    public ResultVo insertRoleMenu(Long roleId,String menusId) {
        ResultVo resultVo = new ResultVo();
        log.info("更新角色菜单权限");
        menuService.insertRoleMenu(roleId,menusId);
        return resultVo;
    }

}
