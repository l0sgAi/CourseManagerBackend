package com.losgai.cs.manager.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentGradeDto {
    private String name;
    private BigDecimal avgGrade;
}