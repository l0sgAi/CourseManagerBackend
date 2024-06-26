package com.losgai.cs.manager.mapper;

import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherCourseMapper {
    List<Course> findByPage(CourseDataDto courseDataDto, Integer id);
}
