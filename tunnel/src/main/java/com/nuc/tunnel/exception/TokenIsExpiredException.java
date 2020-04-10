package com.nuc.tunnel.exception;

/**
 * token过期异常
 */
public class TokenIsExpiredException extends RuntimeException {

    public TokenIsExpiredException(String msg){
        super(msg);
    }

    public TokenIsExpiredException(String msg, Throwable t){
        super(msg,t);
    }
}
