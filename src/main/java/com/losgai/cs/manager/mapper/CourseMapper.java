package com.losgai.cs.manager.mapper;

import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.CourseNumDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> findByPage(CourseDataDto courseDataDto);

    List<Course> findAllByPage(CourseDataDto courseDataDto);

    void save(Course course);

    void update(Course course);

    void deleteById(Integer dataId);

    List<Course> findAll();

    List<CourseNumDto> getTopTenCourses();
}
