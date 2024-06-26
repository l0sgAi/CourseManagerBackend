package com.losgai.cs.manager.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Student {
    Integer id;
    String name;
    String password;
    String avatar;
    Integer sex;
    String college;
    String major;
    String studentClass;
    String createTime;
    String updateTime;
    Integer isDeleted;
}
