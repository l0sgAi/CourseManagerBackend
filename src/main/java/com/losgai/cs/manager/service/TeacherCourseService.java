package com.losgai.cs.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;

public interface TeacherCourseService {
    PageInfo<Course> findByPage(CourseDataDto courseDataDto, Integer current, Integer limit, Integer id);
}
