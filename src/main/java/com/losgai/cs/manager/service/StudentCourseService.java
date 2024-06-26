package com.losgai.cs.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.StudentCourseGradeDto;

import java.util.List;

public interface StudentCourseService {
    PageInfo<Course> findByPage(CourseDataDto courseDataDto, Integer current, Integer limit, Integer id);

    void saveStudentCourseData(Integer id, Course course);

    void deleteStudentCourseById(Integer id, Integer dataId);

    List<Course> findPickedList(Integer id);

    PageInfo<StudentCourseGradeDto> findGradeByPage(CourseDataDto courseDataDto, Integer current, Integer limit, Integer id);
}
