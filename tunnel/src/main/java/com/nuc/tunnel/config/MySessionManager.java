package com.nuc.tunnel.config;

import org.apache.shiro.session.mgt.DefaultSessionManager;

public class MySessionManager extends DefaultSessionManager {
    private static final String TOKEN = "token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
        super();
    }

//    @Override
//    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
//        String id = WebUtils.toHttp(request).getHeader(TOKEN);
//        //如果请求头中有 token 则其值为sessionId
//        if (!StringUtils.isEmpty(id)) {
//            request.setAttribute(REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
//            return id;
//        } else {
//            //否则按默认规则从cookie取sessionId
//            return super.getSessionId(request, response);
//        }
//    }


}
