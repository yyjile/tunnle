package com.nuc.tunnel.exception;

/**
 * Created by LXX on 2018/10/23.
 */
public class NoneTokenException extends RuntimeException{

    public NoneTokenException(String msg){
        super(msg);
    }

    public NoneTokenException(String msg, Throwable t){
        super(msg,t);
    }
}
