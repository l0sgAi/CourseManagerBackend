package com.losgai.cs.manager.entity.vo;

import lombok.Data;

@Data
public class LoginVo { //登录结果实体类

    private String token ;

    private String refresh_token ;

}