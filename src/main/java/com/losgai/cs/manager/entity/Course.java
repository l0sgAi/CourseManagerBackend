package com.losgai.cs.manager.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Course {
    Integer id;
    String name;
    BigDecimal credit;
    String intro;
    String courseTime;
    String courseLocation;
    Integer numLimit;
    Integer num;
    String report;
    String createTime;
    String updateTime;
    Integer isDeleted;
}
