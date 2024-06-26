package com.losgai.cs.manager.mapper;

import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.entity.dto.StudentGradeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    Student selectStudentInfoByIdAndName(int id, String name);

    int updatePassword(PasswordDto passwordDto);

    List<Student> findAllByPage(CourseDataDto courseDataDto);

    void save(Student course);

    void update(Student course);

    void deleteById(Integer dataId);

    List<StudentGradeDto> getTopTenStudents();
}
