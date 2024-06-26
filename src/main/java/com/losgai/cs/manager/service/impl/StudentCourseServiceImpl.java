package com.losgai.cs.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.common.exception.SysException;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.StudentCourseGradeDto;
import com.losgai.cs.manager.mapper.StudentCourseMapper;
import com.losgai.cs.manager.service.CourseService;
import com.losgai.cs.manager.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private CourseService courseService;

    @Override
    public PageInfo<Course> findByPage(CourseDataDto courseDataDto, Integer current, Integer limit, Integer id) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<Course> courseList=studentCourseMapper.findByPage(courseDataDto,id);
        //封装pageInfo对象
        return new PageInfo<>(courseList);
    }

    @Override
    @Transactional
    public void saveStudentCourseData(Integer id, Course course) { //学生选课，需要控制选课人数
        List<Course> courseCurrentList = courseService.findAllCourse(); //获取当前的选课人数
        Course courseCurrent = courseCurrentList.stream()
                .filter(obj -> Objects.equals(obj.getId(), course.getId()))
                .findFirst()
                .orElse(null);

        //防止产生空指针
        Integer num = null;
        if (courseCurrent != null) {
            num = courseCurrent.getNum();
        }
        Integer numLimit = null;
        if (courseCurrent != null) {
            numLimit = courseCurrent.getNumLimit();
        }
        if(num>=numLimit){
            throw new SysException(ResultCodeEnum.DATA_ERROR);
        }
        Integer courseId = course.getId();
        studentCourseMapper.saveStudentCourseData(id,courseId);
        studentCourseMapper.updateNumAdd(courseId);
    }

    @Override
    @Transactional
    public void deleteStudentCourseById(Integer id, Integer dataId) { //退课
        studentCourseMapper.deleteStudentCourseByStudentId(id,dataId);
        studentCourseMapper.updateNumSub(dataId);
    }

    @Override
    public List<Course> findPickedList(Integer id) {
        return studentCourseMapper.findPickedCourseByStudentId(id);
    }

    @Override
    public PageInfo<StudentCourseGradeDto> findGradeByPage(CourseDataDto courseDataDto, Integer current, Integer limit, Integer id) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<StudentCourseGradeDto> courseGradeList=studentCourseMapper.findGradeByPage(courseDataDto,id);
        //封装pageInfo对象
        return new PageInfo<>(courseGradeList);
    }
}
