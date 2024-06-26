package com.losgai.cs.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.vo.CourseStatisticsVo;

import java.util.List;
import java.util.Map;

public interface CourseService {
    PageInfo<Course> findByPage(CourseDataDto courseDataDto, Integer current, Integer limit);

    PageInfo<Course> findAllByPage(CourseDataDto courseDataDto, Integer current, Integer limit);

    void saveCourseData(Course course);

    void updateCourseData(Course course);

    void deleteCourseById(Integer dataId);

    List<Course> findAllCourse();

    CourseStatisticsVo getCourseStatistics();
}
