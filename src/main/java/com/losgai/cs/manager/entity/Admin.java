package com.losgai.cs.manager.entity;

import lombok.Data;

@Data
public class Admin {
    Integer id;
    String name;
    String password;
    String avatar;
    String createTime;
    String updateTime;
    Integer isDeleted;
}
