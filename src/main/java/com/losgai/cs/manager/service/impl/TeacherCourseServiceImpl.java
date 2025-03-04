package com.losgai.cs.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.dto.AssignGradeDto;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.IdsAndGrades;
import com.losgai.cs.manager.mapper.TeacherCourseMapper;
import com.losgai.cs.manager.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;
    @Override
    public PageInfo<Course> findByPage(CourseDataDto courseDataDto, Integer current, Integer limit, Integer id) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<Course> courseList=teacherCourseMapper.findByPage(courseDataDto,id);
        //封装pageInfo对象
        return new PageInfo<>(courseList);
    }

    @Override
    public List<Student> findCourseStudent(Integer courseId) {
        return teacherCourseMapper.findCourseStudents(courseId);
    }

    @Override
    public void doAssign(AssignGradeDto assignGradeDto) {
        //保存分配数据
        List<IdsAndGrades> IdsAndGradesList = assignGradeDto.getStudentGradeList();
        if(IdsAndGradesList != null && !IdsAndGradesList.isEmpty()){ //学生分配了分数
            teacherCourseMapper.doAssign(assignGradeDto);
        }
    }
}
