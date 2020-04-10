package com.nuc.tunnel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable{

    private String userName;

    private String userNo;
}
