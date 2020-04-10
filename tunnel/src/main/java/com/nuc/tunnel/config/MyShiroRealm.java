package com.nuc.tunnel.config;

import com.nuc.tunnel.entity.Employee;
import com.nuc.tunnel.entity.Role;
import com.nuc.tunnel.exception.NoneTokenException;
import com.nuc.tunnel.exception.WrongUserNoException;
import com.nuc.tunnel.service.EmployeeService;
import com.nuc.tunnel.service.MenuService;
import com.nuc.tunnel.until.AuthErrorEnum;
import com.nuc.tunnel.until.MenuVo;
import com.nuc.tunnel.until.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@Configuration
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MenuService menuService;

    /**
     *用户认证
     * @param authenticationToken 认证信息token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ResultVo resultVo = new ResultVo();
        log.error("接收的token  "+authenticationToken);
        //在Post请求的时候会先进认证，然后再到请求
        if (authenticationToken.getPrincipal() == null){
            throw new NoneTokenException(AuthErrorEnum.TOKEN_NEEDED.getMessage());
        }
        //获取用户信息
        String userName = authenticationToken.getPrincipal().toString();
        Employee employee = employeeService.selectByUser(userName);
        String pass = employee.getPassword();
        if (employee == null){
            throw new WrongUserNoException(AuthErrorEnum.LOGIN_NO_ERROR.getMessage());
        }
        //验证authenticationToken和simpleAuthenticationInfo的信息是否匹配
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,pass,getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 用户角色菜单权限,授权
     * @param principalCollection 授权
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.error("权限授权principalCollection: "+principalCollection);
        String userName = (String) principalCollection.getPrimaryPrincipal();
        log.error("权限授权userName: "+userName);
        Employee employee = employeeService.selectByUser(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = employeeService.findRoleByEmployeeId(employee.getId());
        List<String> roleNames = new ArrayList<>();
        List<String> menus = new ArrayList<>();
        for (Role role : roles){
            roleNames.add(role.getRoleName());
            List<MenuVo>  menuVos= menuService.findMenuTree(role.getId());
            for (MenuVo menu : menuVos){
                menus.add(menu.getText());
            }
        }
        simpleAuthorizationInfo.addRoles(roleNames);
        simpleAuthorizationInfo.addStringPermissions(menus);
        log.info("---simpleAuthorizationInfo"+simpleAuthorizationInfo);
        return simpleAuthorizationInfo;
    }
}
