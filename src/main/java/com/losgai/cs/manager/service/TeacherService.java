package com.losgai.cs.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Teacher;
import com.losgai.cs.manager.entity.dto.AssignCourseDto;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;

import java.util.List;

public interface TeacherService {
    void updatePassword(PasswordDto passwordDto);

    PageInfo<Teacher> findAllByPage(CourseDataDto courseDataDto, Integer current, Integer limit);

    void saveTeacherData(Teacher teacher);

    void updateTeacherData(Teacher teacher);

    void deleteTeacherById(Integer dataId);

    List<Course> findTeacherCourse(Integer teacherId);

    void doAssign(AssignCourseDto assignCourseDto);
}
