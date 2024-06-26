package com.losgai.cs.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.CourseNumDto;
import com.losgai.cs.manager.entity.dto.StudentGradeDto;
import com.losgai.cs.manager.entity.vo.CourseStatisticsVo;
import com.losgai.cs.manager.entity.vo.StudentStatisticsVo;
import com.losgai.cs.manager.mapper.CourseMapper;
import com.losgai.cs.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Override
    public PageInfo<Course> findByPage(CourseDataDto courseDataDto, Integer current, Integer limit) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<Course> courseList=courseMapper.findByPage(courseDataDto);
        //封装pageInfo对象
        return new PageInfo<>(courseList);
    }

    @Override
    public PageInfo<Course> findAllByPage(CourseDataDto courseDataDto, Integer current, Integer limit) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<Course> courseList=courseMapper.findAllByPage(courseDataDto);
        //封装pageInfo对象
        return new PageInfo<>(courseList);
    }

    @Override
    public void saveCourseData(Course course) {
        courseMapper.save(course);
    }

    @Override
    public void updateCourseData(Course course) {
        courseMapper.update(course);
    }

    @Override
    public void deleteCourseById(Integer dataId) {
        courseMapper.deleteById(dataId);
    }

    @Override
    public List<Course> findAllCourse() {
        return courseMapper.findAll();
    }

    @Override
    public CourseStatisticsVo getCourseStatistics() {
        //分别获取10个课程的名称和选课人数
        List<String> courseNameList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        List<CourseNumDto> topCourses = courseMapper.getTopTenCourses();
        for (CourseNumDto courseNumDto : topCourses) {
            courseNameList.add(courseNumDto.getName());
            numList.add(courseNumDto.getNum());
        }

        //把2个list集合封装至Vo中
        CourseStatisticsVo courseStatisticsVo = new CourseStatisticsVo();
        courseStatisticsVo.setAmountList(numList);
        courseStatisticsVo.setCourseList(courseNameList);
        return courseStatisticsVo;
    }
}
