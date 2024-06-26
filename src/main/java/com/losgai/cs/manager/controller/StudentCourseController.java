package com.losgai.cs.manager.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.StudentCourseGradeDto;
import com.losgai.cs.manager.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/studentCourse")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("/findByPage/{current}/{limit}/{id}")
    //学生主页通知查询
    //当前页current和每页的记录数limit 根据title模糊查询
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @PathVariable("id") Integer id,
                             @RequestBody CourseDataDto courseDataDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        //这里只查询学生选择的课程
        PageInfo<Course> pageInfo=studentCourseService.findByPage(courseDataDto,current, limit,id);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findPickedList/{id}")
    //学生所有选择的课程查询
    public Result findPickedList(@PathVariable("id") Integer id) {
        List<Course> pickedList=studentCourseService.findPickedList(id);
        return Result.build(pickedList, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/findGradeByPage/{current}/{limit}/{id}")
    //学生成绩列表查询
    //当前页current和每页的记录数limit 根据title模糊查询
    public Result findGradeByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @PathVariable("id") Integer id,
                             @RequestBody CourseDataDto courseDataDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        //这里只查询学生选择的课程
        PageInfo<StudentCourseGradeDto> pageInfo=studentCourseService.findGradeByPage(courseDataDto,current, limit,id);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //选课记录添加的方法
    @PostMapping("/save/{id}")
    public Result saveCourse( @PathVariable("id") Integer id,
                              @RequestBody Course course) {
        studentCourseService.saveStudentCourseData(id,course);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //退课方法
    @DeleteMapping("/deleteCourseById/{dataId}/{id}")
    public Result deleteCourse(@PathVariable("dataId") Integer dataId,
                               @PathVariable("id") Integer id) {
        studentCourseService.deleteStudentCourseById(id,dataId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
