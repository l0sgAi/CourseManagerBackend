package com.losgai.cs.manager.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseStatisticsVo {

    private List<String> courseList;

    private List<Integer> amountList ;
}
