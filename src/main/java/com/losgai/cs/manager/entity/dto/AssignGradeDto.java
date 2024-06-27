package com.losgai.cs.manager.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignGradeDto {

    private Integer courseId;			// 课程id

    private List<IdsAndGrades> studentGradeList; //IdsAndGrades包括打分学生id Integer、成绩 BigDecimal

}