package com.losgai.cs.manager.controller;


import com.losgai.cs.manager.entity.*;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.dto.LoginDto;
import com.losgai.cs.manager.entity.vo.LoginVo;
import com.losgai.cs.manager.entity.vo.ValidateCodeVo;
import com.losgai.cs.manager.service.IndexService;
import com.losgai.cs.manager.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/system/index")
public class IndexController { //基础功能控制器
    @Autowired
    private IndexService indexService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping("/generateValidateCode")
    public Result generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = indexService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    //用户退出
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        indexService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getStudentInfo")
    public Result getStudent(@RequestHeader(name = "token") String token) { //从ThreadLocal里面直接获取信息
        Student studentInfo = indexService.getStudentInfo(token);
        return Result.build(studentInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getTeacherInfo")
    public Result getTeacher(@RequestHeader(name = "token") String token) { //从ThreadLocal里面直接获取信息
        Teacher teacherInfo = indexService.getTeacherInfo(token);
        return Result.build(teacherInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getAdminInfo")
    public Result getAdmin(@RequestHeader(name = "token") String token) { //从ThreadLocal里面直接获取信息
        Admin adminInfo = indexService.getAdminInfo(token);
        return Result.build(adminInfo, ResultCodeEnum.SUCCESS);
    }
}
