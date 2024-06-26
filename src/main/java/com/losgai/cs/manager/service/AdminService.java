package com.losgai.cs.manager.service;

import com.losgai.cs.manager.entity.Admin;
import com.losgai.cs.manager.entity.dto.PasswordDto;

public interface AdminService {
    void updatePassword(PasswordDto passwordDto);
}
