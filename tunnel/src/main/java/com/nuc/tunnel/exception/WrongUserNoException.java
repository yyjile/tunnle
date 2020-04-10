package com.nuc.tunnel.exception;


/**
 * Created by LXX on 2018/10/23.
 */
public class WrongUserNoException extends RuntimeException {

    public WrongUserNoException(String msg){
        super(msg);
    }

    public WrongUserNoException(String msg, Throwable t){
        super(msg,t);
    }
}
