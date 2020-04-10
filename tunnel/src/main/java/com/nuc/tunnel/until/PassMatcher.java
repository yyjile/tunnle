package com.nuc.tunnel.until;

/**
 * Created by: NanLeiLei
 * Company: MJ
 * Date: 2019/03/08
 * Time: 11:14
 * mj专用加密算法
 *
 */
public class PassMatcher {
    public static String getPass(String userName,String password){
        return MD5.encode(userName+password+"mj专用加密算法");
    }


    public static void main(String[] args) {
        String userName="lisi";
        String password="123456";
        System.out.println(MD5.encode(userName+password+"mj专用加密算法"));
    }
}
