package com.losgai.cs.manager.controller;


import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Teacher;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.dto.AssignCourseDto;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PutMapping("/updateTeacherPassword")
    public Result updateSysRole(@RequestBody PasswordDto passwordDto) {
        teacherService.updatePassword(passwordDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //1,列表查询方法
    @PostMapping("/findAllByPage/{current}/{limit}")
    //当前页current和每页的记录数limit 根据title模糊查询
    public Result findAllByPage(@PathVariable("current") Integer current,
                                @PathVariable("limit") Integer limit,
                                @RequestBody CourseDataDto courseDataDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        PageInfo<Teacher> pageInfo=teacherService.findAllByPage(courseDataDto,current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //2.记录添加的方法
    @PostMapping("/save")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        teacherService.saveTeacherData(teacher);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3.记录修改方法
    @PutMapping ("/update")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateTeacherData(teacher);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //4.记录删除方法
    @DeleteMapping("/deleteTeacherById/{dataId}")
    public Result deleteTeacher(@PathVariable("dataId") Integer dataId) {
        teacherService.deleteTeacherById(dataId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查询教师拥有的课程
    @GetMapping("/findTeacherCourse/{teacherId}")
    public Result findAll(@PathVariable("teacherId") Integer teacherId) {
        List<Course> teacherCourseList = teacherService.findTeacherCourse(teacherId);
        return Result.build(teacherCourseList, ResultCodeEnum.SUCCESS);
    }

    //教师管理：分配菜单
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignCourseDto assignCourseDto) {
        teacherService.doAssign(assignCourseDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
