package com.nuc.tunnel.until;



/**
 * @Author: admin
 * @Description: 包装类
 * @Date: Created in 8:52 2018/3/29
 * @Modified By:
 */
public class ResultVo<T> {

    /**
     * 发给前端的数据
     */
    private T data;
    /**
     * 发给前端的状态
     */
    private int status;

    /**
     * 发给前端的信息
     */
    private String msg;

    public ResultVo() {
        this.status = 200;
        this.msg = "请求成功!";
    }

    public ResultVo(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResultVo(String msg, int status, T data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public ResultVo(T data) {
        this.status = 200;
        this.msg = "请求成功";
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
