package com.losgai.cs.manager.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignCourseDto {

    private Integer teacherId;			// 教师id

    private List<Integer> courseIdList;
}