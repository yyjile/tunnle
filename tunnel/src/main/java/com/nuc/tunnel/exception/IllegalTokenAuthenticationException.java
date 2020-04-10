package com.nuc.tunnel.exception;

/**
 * Created by LXX on 2018/10/22.
 */
public class IllegalTokenAuthenticationException extends RuntimeException {

    public IllegalTokenAuthenticationException(String msg){
        super(msg);
    }

    public IllegalTokenAuthenticationException(String msg, Throwable t){
        super(msg,t);
    }
}
