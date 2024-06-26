package com.losgai.cs.manager.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/system/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    //1.列表搜索方法 课程通知
    //当前页current和每页的记录数limit
    @PostMapping("/findByPage/{current}/{limit}")
    //当前页current和每页的记录数limit 根据title模糊查询
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody CourseDataDto courseDataDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        PageInfo<Course> pageInfo=courseService.findByPage(courseDataDto,current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/findAllByPage/{current}/{limit}")
    //当前页current和每页的记录数limit 根据title模糊查询
    public Result findAllByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody CourseDataDto courseDataDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        PageInfo<Course> pageInfo=courseService.findAllByPage(courseDataDto,current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //2.记录添加的方法
    @PostMapping("/save")
    public Result saveCourse(@RequestBody Course course) {
        courseService.saveCourseData(course);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3.记录修改方法
    @PutMapping ("/update")
    public Result updateCourse(@RequestBody Course course) {
        courseService.updateCourseData(course);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //4.记录删除方法
    @DeleteMapping("/deleteCourseById/{dataId}")
    public Result deleteCourse(@PathVariable("dataId") Integer dataId) {
        courseService.deleteCourseById(dataId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查询所有课程
    @GetMapping("/findAllCourse")
    public Result findAll() {
        List<Course> courseList = courseService.findAllCourse();
        return Result.build(courseList, ResultCodeEnum.SUCCESS);
    }
}
