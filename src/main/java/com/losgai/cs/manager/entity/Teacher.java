package com.losgai.cs.manager.entity;

import lombok.Data;

@Data
public class Teacher {
    Integer id;
    String name;
    String password;
    String avatar;
    Integer sex;
    String college;
    String degree;
    String title;
    String email;
    String phone;
    String intro;
    String createTime;
    String updateTime;
    Integer isDeleted;
}
