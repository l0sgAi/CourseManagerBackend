package com.losgai.cs.manager.mapper;

import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.StudentCourseGradeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentCourseMapper {
    List<Course> findByPage(CourseDataDto courseDataDto, Integer id);

    void saveStudentCourseData(Integer id, Integer courseId);

    void updateNumAdd(Integer courseId);

    void deleteStudentCourseByStudentId(Integer id, Integer dataId);

    void updateNumSub(Integer dataId);

    List<Course> findPickedCourseByStudentId(Integer id);

    List<StudentCourseGradeDto> findGradeByPage(CourseDataDto courseDataDto, Integer id);
}
