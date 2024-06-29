package com.losgai.cs.manager.service.impl;

import com.losgai.cs.manager.entity.Admin;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.common.exception.SysException;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.mapper.AdminMapper;
import com.losgai.cs.manager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void updatePassword(PasswordDto passwordDto) {
        String password_encrypt_original = DigestUtils.md5DigestAsHex(passwordDto.getOriginalPassword().getBytes());
        passwordDto.setOriginalPassword(password_encrypt_original); //旧密码加密
        String password_encrypt = DigestUtils.md5DigestAsHex(passwordDto.getPassword().getBytes()); //更新密码加密
        passwordDto.setPassword(password_encrypt); //新密码加密
        int i = adminMapper.updatePassword(passwordDto); //更新密码，返回更新条数
        if (i == 0) {
            throw new SysException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
