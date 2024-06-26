package com.losgai.cs.manager.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentCourseGradeDto { //显示的包括成绩的课程数据
    Integer id;
    String name;
    BigDecimal credit;
    String courseTime;
    String courseLocation;
    BigDecimal grade;
}
