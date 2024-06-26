package com.losgai.cs.manager.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Teacher;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.common.exception.SysException;
import com.losgai.cs.manager.entity.dto.AssignCourseDto;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.mapper.TeacherMapper;
import com.losgai.cs.manager.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void updatePassword(PasswordDto passwordDto) {
        String password_encrypt_original = DigestUtils.md5DigestAsHex(passwordDto.getOriginalPassword().getBytes());
        passwordDto.setOriginalPassword(password_encrypt_original); //旧密码加密
        String password_encrypt = DigestUtils.md5DigestAsHex(passwordDto.getPassword().getBytes());
        passwordDto.setPassword(password_encrypt); //新密码加密
        int i = teacherMapper.updatePassword(passwordDto); //更新密码，返回更新条数
        if (i == 0) {
            throw new SysException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public PageInfo<Teacher> findAllByPage(CourseDataDto courseDataDto, Integer current, Integer limit) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<Teacher> teachers=teacherMapper.findAllByPage(courseDataDto);
        //封装pageInfo对象
        return new PageInfo<>(teachers);
    }

    @Override
    public void saveTeacherData(Teacher teacher) {
        teacher.setPassword(DigestUtils.md5DigestAsHex(teacher.getPassword().getBytes()));//数据加密
        teacherMapper.save(teacher);
    }

    @Override
    public void updateTeacherData(Teacher teacher) {
        if(ObjectUtil.isNotNull(teacher.getPassword())){ //如果密码不为空，则加密
            teacher.setPassword(DigestUtils.md5DigestAsHex(teacher.getPassword().getBytes()));//数据加密
        }
        teacherMapper.update(teacher);
    }

    @Override
    public void deleteTeacherById(Integer dataId) {
        teacherMapper.deleteById(dataId);
    }

    @Override
    public List<Course> findTeacherCourse(Integer teacherId) {
        return teacherMapper.findTeacherCourses(teacherId);
    }

    @Override
    @Transactional
    public void doAssign(AssignCourseDto assignCourseDto) {
        //删除角色之前分配过的菜单数据
        teacherMapper.deleteCourseByTeacherId(assignCourseDto.getTeacherId());

        //保存分配数据
        List<Integer> courseIds = assignCourseDto.getCourseIdList();
        if(courseIds != null && !courseIds.isEmpty()){ //角色分配了菜单
            teacherMapper.doAssign(assignCourseDto);
        }

    }
}
