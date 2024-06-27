package com.losgai.cs.manager.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IdsAndGrades { //封装学生id和成绩信息
    Integer id;
    BigDecimal grade;
}
