package com.losgai.cs.manager.controller;

import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.vo.CourseStatisticsVo;
import com.losgai.cs.manager.entity.vo.StudentStatisticsVo;
import com.losgai.cs.manager.service.CourseService;
import com.losgai.cs.manager.service.StudentService;
import com.losgai.cs.manager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/statistics")
public class StatisticController { //数据统计

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    // 订单统计
    @GetMapping("/getStatisticsDataStudent") //学生
    public Result getOrderStatisticsDataStudent() {
        StudentStatisticsVo studentStatisticsVo =  //返回Vo封装数据
                studentService.getStudentStatistics();
        return Result.build(studentStatisticsVo, ResultCodeEnum.SUCCESS);
    }

    // 订单统计
    @GetMapping("/getStatisticsDataCourse") //课程
    public Result getOrderStatisticsDataCourse() {
        CourseStatisticsVo courseStatisticsVo =  //返回Vo封装数据
                courseService.getCourseStatistics();
        return Result.build(courseStatisticsVo, ResultCodeEnum.SUCCESS);
    }




}
