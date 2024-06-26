package com.losgai.cs.manager.service;

import com.losgai.cs.manager.entity.*;
import com.losgai.cs.manager.entity.dto.LoginDto;
import com.losgai.cs.manager.entity.vo.LoginVo;

import java.util.List;

public interface IndexService {
    LoginVo login(LoginDto loginDto);

    void logout(String token);

    Teacher getTeacherInfo(String token);

    Student getStudentInfo(String token);

    Admin getAdminInfo(String token);

}
