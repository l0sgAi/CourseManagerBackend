package com.losgai.cs.manager.controller;

import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    //修改自己的密码
    @PutMapping("/updateStudentPassword")
    public Result updateSysRole(@RequestBody PasswordDto passwordDto) {
        studentService.updatePassword(passwordDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //1,列表查询方法
    @PostMapping("/findAllByPage/{current}/{limit}")
    //当前页current和每页的记录数limit 根据title模糊查询
    public Result findAllByPage(@PathVariable("current") Integer current,
                                @PathVariable("limit") Integer limit,
                                @RequestBody CourseDataDto courseDataDto) {
        //dto封装页面传入的数据 vo封装返回的数据
        PageInfo<Student> pageInfo=studentService.findAllByPage(courseDataDto,current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //2.记录添加的方法
    @PostMapping("/save")
    public Result saveStudent(@RequestBody Student student) {
        studentService.saveStudentData(student);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //3.记录修改方法
    @PutMapping ("/update")
    public Result updateStudent(@RequestBody Student student) {
        studentService.updateStudentData(student);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //4.记录删除方法
    @DeleteMapping("/deleteStudentById/{dataId}")
    public Result deleteStudent(@PathVariable("dataId") Integer dataId) {
        studentService.deleteStudentById(dataId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
