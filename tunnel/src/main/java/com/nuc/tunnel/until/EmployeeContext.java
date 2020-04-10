package com.nuc.tunnel.until;


import javax.servlet.http.HttpServletRequest;


public class EmployeeContext {
    /**
     * 后台登陆的信息
     */
    public static final String USERSESSION ="USER_SESSION";

    /**
     * 用户菜单信息集合
     */
    public static final String MENUSESSION = "MENU_SESSION";

    public static final ThreadLocal<HttpServletRequest> local = new ThreadLocal<>();

    /**
     * 将当前的对象与线程绑定
     * @param request
     */
    public static  void setRequest(HttpServletRequest request){
        local.set(request);
    }

    /**
     * get方法获取对象
     * @return
     */
    public static HttpServletRequest getRequest(){
        return local.get();
    }

    /**
     * 将当前的对象与线程解绑
     */
    public static void removeRequest(){
        local.remove();
    }

}
