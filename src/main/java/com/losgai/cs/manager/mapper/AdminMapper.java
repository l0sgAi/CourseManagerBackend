package com.losgai.cs.manager.mapper;

import com.losgai.cs.manager.entity.Admin;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    Admin selectAdminInfoByIdAndName(int id, String name);

    int updatePassword(PasswordDto passwordDto);
}
