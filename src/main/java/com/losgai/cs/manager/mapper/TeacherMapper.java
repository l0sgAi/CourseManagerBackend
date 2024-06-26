package com.losgai.cs.manager.mapper;

import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Teacher;
import com.losgai.cs.manager.entity.dto.AssignCourseDto;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {

    Teacher selectTeacherInfoByIdAndName(int id, String name);

    int updatePassword(PasswordDto passwordDto);

    List<Teacher> findAllByPage(CourseDataDto courseDataDto);

    void save(Teacher teacher);

    void update(Teacher teacher);

    void deleteById(Integer dataId);

    List<Course> findTeacherCourses(Integer teacherId);

    void deleteCourseByTeacherId(Integer teacherId);

    void doAssign(AssignCourseDto assignCourseDto);
}
