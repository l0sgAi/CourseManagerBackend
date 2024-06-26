package com.losgai.cs.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.losgai.cs.manager.entity.*;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import com.losgai.cs.manager.entity.common.exception.SysException;
import com.losgai.cs.manager.entity.dto.LoginDto;
import com.losgai.cs.manager.entity.vo.LoginVo;
import com.losgai.cs.manager.interceptor.AuthContextUtil;
import com.losgai.cs.manager.mapper.AdminMapper;
import com.losgai.cs.manager.mapper.StudentMapper;
import com.losgai.cs.manager.mapper.TeacherMapper;
import com.losgai.cs.manager.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private AdminMapper adminMapper;

    //登录流程
    @Transactional
    @Override
    public LoginVo login(LoginDto loginDto) {
        //0.1 获取用户输入的验证码和存储的Redis key名称
        String captcha = loginDto.getCaptcha(); //输入的验证码
        String key = loginDto.getCodeKey(); //Redis key名称

        //0.2 根据Redis获取到的key,查询Redis存储的验证码(从Redis中根据Key取值)
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //0.3 比较用户输入的验证码和Redis存储的验证码是否一致
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(captcha, redisCode)){
            //判空或者验证码错误的情况(比较不分大小写)
            //如果不一致，登录失败,返回错误信息
            throw new SysException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
        //0.4 如果一致，登录成功，删除Redis中的验证码
        redisTemplate.delete("user:validate" + key);

        //1.从LoginDto中获取用户名(姓名+id)与登录角色(教师/学生/管理员)
        String username = loginDto.getUserName();
        Integer loginType = loginDto.getLoginType();
        System.out.println("\n***loginType = ***\n" + loginType);


        //2.根据用户名、id和登录角色查询数据库表
       if(loginType==0){
           try {
               int index = parseString(username);
               int id = Integer.parseInt(username.substring(index));
               String name = username.substring(0,index);

               Admin admin=adminMapper.selectAdminInfoByIdAndName(id,name);
               //3.如果根据用户名查询不到，用户不存在，返回null
               if(admin==null){
                   throw new SysException(ResultCodeEnum.LOGIN_ERROR);
               }
               //如果查询到用户信息，则用户存在，比较输入密码和数据库密码是否一致
               String db_password=admin.getPassword();
               return validateAdmin(db_password,loginDto,admin);
           } catch (Exception e) {
               throw new SysException(ResultCodeEnum.LOGIN_ERROR);
           }
       } else if (loginType==1) {
           try {
               int index = parseString(username);
               int id = Integer.parseInt(username.substring(index));
               String name = username.substring(0,index);

               Student student=studentMapper.selectStudentInfoByIdAndName(id,name);
               if(student==null){
                   throw new SysException(ResultCodeEnum.LOGIN_ERROR);
               }
               //如果查询到用户信息，则用户存在，比较输入密码和数据库密码是否一致
               String db_password=student.getPassword();
               return validateStudent(db_password,loginDto,student);
           } catch (Exception e) {
               throw new SysException(ResultCodeEnum.LOGIN_ERROR);
           }
       } else if (loginType==2) {
           try {
               int index = parseString(username);
               int id = Integer.parseInt(username.substring(index));
               String name = username.substring(0,index);

               Teacher teacher=teacherMapper.selectTeacherInfoByIdAndName(id,name);
               if(teacher==null){
                   throw new SysException(ResultCodeEnum.LOGIN_ERROR);
               }
               //如果查询到用户信息，则用户存在，比较输入密码和数据库密码是否一致
               String db_password=teacher.getPassword();
               return validateTeacher(db_password,loginDto,teacher);
           } catch (Exception e) {
               throw new SysException(ResultCodeEnum.LOGIN_ERROR);
           }
       }
        return null;
    }

    //登出流程，清除redis中的token
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }

    @Override
    public Teacher getTeacherInfo(String token) {
        return AuthContextUtil.getTeacher(); //线程获取用户信息
    }

    @Override
    public Student getStudentInfo(String token) {
        return AuthContextUtil.getStudent();
    }

    @Override
    public Admin getAdminInfo(String token) {
        return AuthContextUtil.getAdmin();
    }

    private int parseString(String str) throws SysException { //字符串解析方法
        int i = 0;
        for (; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                break;
            }
        }

        if (i == str.length()) {
            throw new SysException(ResultCodeEnum.DATA_ERROR);
        }

//        return Integer.parseInt(str.substring(i));
        return i;
    }

    private LoginVo validateAdmin(String db_password, LoginDto loginDto, Admin admin){
        //将输入密码MD5加密，再比对
        String input_password= DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!db_password.equals(input_password)){
            //密码不一致，登录失败
            throw new SysException(ResultCodeEnum.LOGIN_ERROR);
        }
        //登录成功，生成唯一标识token
        String token = UUID.randomUUID().toString().replace("-","");

        //登录成功信息放入redis
        //key : token|value: 用户信息
        redisTemplate.opsForValue()
                .set("admin:login"+token, JSON.toJSONString(admin),
                        7, TimeUnit.DAYS); //设置7天过期，当日有效

        //返回loginVo对象
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    private LoginVo validateStudent(String db_password, LoginDto loginDto, Student student){
        //将输入密码MD5加密，再比对
        String input_password= DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!db_password.equals(input_password)){
            //密码不一致，登录失败
            throw new SysException(ResultCodeEnum.LOGIN_ERROR);
        }
        //登录成功，生成唯一标识token
        String token = UUID.randomUUID().toString().replace("-","");

        //登录成功信息放入redis
        //key : token|value: 用户信息
        redisTemplate.opsForValue()
                .set("student:login"+token, JSON.toJSONString(student),
                        7, TimeUnit.DAYS); //设置7天过期，当日有效

        //返回loginVo对象
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    private LoginVo validateTeacher(String db_password, LoginDto loginDto, Teacher teacher){
        //将输入密码MD5加密，再比对
        String input_password= DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!db_password.equals(input_password)){
            //密码不一致，登录失败
            throw new SysException(ResultCodeEnum.LOGIN_ERROR);
        }
        //登录成功，生成唯一标识token
        String token = UUID.randomUUID().toString().replace("-","");

        //登录成功信息放入redis
        //key : token|value: 用户信息
        redisTemplate.opsForValue()
                .set("teacher:login"+token, JSON.toJSONString(teacher),
                        7, TimeUnit.DAYS); //设置7天过期，当日有效

        //返回loginVo对象
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

}
