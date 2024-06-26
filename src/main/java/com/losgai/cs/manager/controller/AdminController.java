package com.losgai.cs.manager.controller;

import com.losgai.cs.manager.entity.Admin;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.service.AdminService;
import com.losgai.cs.manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/system/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/updateAdminPassword")
    public Result updateSysRole(@RequestBody PasswordDto passwordDto) {
        adminService.updatePassword(passwordDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
