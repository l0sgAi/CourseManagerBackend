package com.losgai.cs.manager.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/teacherCourse")
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @PostMapping("/findByPage/{current}/{limit}/{id}")
    //学生主页通知查询
    //当前页current和每页的记录数limit 根据title模糊查询
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @PathVariable("id") Integer id,
                             @RequestBody CourseDataDto courseDataDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        //这里只查询教师拥有的的课程
        PageInfo<Course> pageInfo=teacherCourseService.findByPage(courseDataDto,current, limit,id);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
