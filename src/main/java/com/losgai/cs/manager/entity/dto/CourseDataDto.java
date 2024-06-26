package com.losgai.cs.manager.entity.dto;

import lombok.Data;

@Data
public class CourseDataDto { //查询数据封装

    private String name; //搜索标题

    private String createTimeBegin; //开始时间

    private String createTimeEnd; //结束时间

}