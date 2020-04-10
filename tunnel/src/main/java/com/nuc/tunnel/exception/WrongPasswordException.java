package com.nuc.tunnel.exception;

/**
 * Created by LXX on 2018/10/23.
 */
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String msg){
        super(msg);
    }

    public WrongPasswordException(String msg,Throwable t){
        super(msg,t);
    }
}
