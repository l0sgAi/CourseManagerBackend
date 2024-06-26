package com.losgai.cs.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.losgai.cs.manager.entity.Course;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.common.exception.SysException;
import com.losgai.cs.manager.entity.dto.CourseDataDto;
import com.losgai.cs.manager.entity.dto.PasswordDto;
import com.losgai.cs.manager.entity.dto.StudentGradeDto;
import com.losgai.cs.manager.entity.vo.StudentStatisticsVo;
import com.losgai.cs.manager.mapper.StudentMapper;
import com.losgai.cs.manager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void updatePassword(PasswordDto passwordDto) {
        String password_encrypt_original = DigestUtils.md5DigestAsHex(passwordDto.getOriginalPassword().getBytes());
        passwordDto.setOriginalPassword(password_encrypt_original); //旧密码加密
        String password_encrypt = DigestUtils.md5DigestAsHex(passwordDto.getPassword().getBytes());
        passwordDto.setPassword(password_encrypt); //新密码加密
        int i = studentMapper.updatePassword(passwordDto); //更新密码，返回更新条数
        if (i == 0) {
            throw new SysException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public PageInfo<Student> findAllByPage(CourseDataDto courseDataDto, Integer current, Integer limit) {
        //设置分页的相关参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有的数据
        List<Student> students=studentMapper.findAllByPage(courseDataDto);
        //封装pageInfo对象
        return new PageInfo<>(students);
    }

    @Override
    public void saveStudentData(Student student) {
        student.setPassword(DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));//数据加密
        studentMapper.save(student);
    }

    @Override
    public void updateStudentData(Student student) {
        student.setPassword(DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));//数据加密
        studentMapper.update(student);
    }

    @Override
    public void deleteStudentById(Integer dataId) {
        studentMapper.deleteById(dataId);
    }

    @Override
    public StudentStatisticsVo getStudentStatistics() {

        //分别获取10名学生的姓名和成绩
        List<String> studentNameList = new ArrayList<>();
        List<BigDecimal> avgGradeList = new ArrayList<>();

        List<StudentGradeDto> topStudents = studentMapper.getTopTenStudents();
        for (StudentGradeDto student : topStudents) {
            studentNameList.add(student.getName());
            avgGradeList.add(student.getAvgGrade());
        }

        //把2个list集合封装至Vo中
        StudentStatisticsVo studentStatisticsVo = new StudentStatisticsVo();
        studentStatisticsVo.setAmountList(avgGradeList);
        studentStatisticsVo.setStudentList(studentNameList);
        return studentStatisticsVo;
    }
}
