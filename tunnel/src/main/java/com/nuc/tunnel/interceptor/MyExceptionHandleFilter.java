package com.nuc.tunnel.interceptor;


import com.nuc.tunnel.exception.*;
import com.nuc.tunnel.until.AuthErrorEnum;
import com.nuc.tunnel.until.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 最外层filter处理验证token、登录认证和授权过滤器中抛出的所有异常
 */
@Slf4j
public class MyExceptionHandleFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }catch (Exception e){
            log.error(e.getMessage());
            ResultVo resultVo = new ResultVo();
            resultVo.setMsg(e.getMessage());
            resultVo.setStatus(500);
            if (e instanceof IOException){
                log.error("导出excel接口：{}，不要返回值,response只能使用一次",httpServletRequest.getRequestURI());
                return;
            }else
            if (e instanceof IllegalTokenAuthenticationException){
                resultVo.setStatus(AuthErrorEnum.AUTH_HEADER_ERROR.getCode());
            }else
            if (e instanceof TokenIsExpiredException){
                if (e.getMessage().equals(AuthErrorEnum.TOKEN_EXPIRED.getMessage())){
                    resultVo.setStatus(AuthErrorEnum.TOKEN_EXPIRED.getCode());
                }else {
                    resultVo.setStatus(AuthErrorEnum.TOKEN_REFRESHED.getCode());
                }
            }else
            if (e instanceof NoneTokenException){
                resultVo.setStatus(AuthErrorEnum.TOKEN_NEEDED.getCode());
            }else
            if (e instanceof WrongUserNoException){
                resultVo.setStatus(AuthErrorEnum.LOGIN_NO_ERROR.getCode());
            }else
            if (e instanceof WrongPasswordException){
                resultVo.setStatus(AuthErrorEnum.LOGIN_PASSWORD_ERROR.getCode());
            }else{
                e.printStackTrace();
            }
//            if (e instanceof SSOAuthenticateFailedException){
//                resultVo.setStatus(AuthErrorEnum.SSO_AUTHENTICATE_FAILED.getCode());
//            }
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("text/html;charset=UTF-8");
//            httpServletResponse.getWriter().write(JsonMapper.obj2String(resultVo));
        }
    }
}
