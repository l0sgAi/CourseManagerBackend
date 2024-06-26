package com.losgai.cs.manager.entity.dto;

import lombok.Data;

@Data
public class PasswordDto { //修改密码实体类
    Integer id;
    String originalPassword;
    String password;
}
