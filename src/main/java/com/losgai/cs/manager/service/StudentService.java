package com.losgai.cs.manager.service;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.entity.vo.StudentStatisticsVo;

public interface StudentService {
    void updatePassword(PasswordDto passwordDto);

    PageInfo<Student> findAllByPage(CourseDataDto courseDataDto, Integer current, Integer limit);

    void saveStudentData(Student student);

    void updateStudentData(Student student);

    void deleteStudentById(Integer dataId);

    StudentStatisticsVo getStudentStatistics();
}
