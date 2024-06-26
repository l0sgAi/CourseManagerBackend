package com.losgai.cs.manager.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StudentStatisticsVo {

    private List<String> studentList;

    private List<BigDecimal> amountList ;
}
