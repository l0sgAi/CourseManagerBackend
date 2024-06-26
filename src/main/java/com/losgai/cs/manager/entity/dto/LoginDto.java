package com.losgai.cs.manager.entity.dto;

import lombok.Data;

@Data
public class LoginDto { //用户登录请求的参数

    private String userName ; //这里的用户名是姓名+id

    private String password ;

    private String captcha ;

    private String codeKey ;

    private Integer loginType;
}