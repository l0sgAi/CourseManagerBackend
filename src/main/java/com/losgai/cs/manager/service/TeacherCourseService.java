package com.losgai.cs.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.dto.AssignGradeDto;
import com.losgai.cs.manager.entity.dto.CourseDataDto;

import java.util.List;

public interface TeacherCourseService {
    PageInfo<Course> findByPage(CourseDataDto courseDataDto, Integer current, Integer limit, Integer id);

    List<Student> findCourseStudent(Integer courseId);

    void doAssign(AssignGradeDto assignGradeDto);
}
