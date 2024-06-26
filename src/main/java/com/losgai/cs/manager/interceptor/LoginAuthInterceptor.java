package com.losgai.cs.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.losgai.cs.manager.entity.Admin;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.Teacher;
import com.losgai.cs.manager.entity.common.Result;
import com.losgai.cs.manager.entity.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception { //前拦截
        //1.获取请求方式，如果请求方式是options 预检请求，直接放行
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }

        //2.从请求头获取token
        String token = request.getHeader("token");

        //3.判断token是否为空，如果为空，返回错误提示
        if(StrUtil.isEmpty(token)){
            //响应错误提示
            responseNoLoginInfo(response);
            return false;
        }

        //4.如果token不为空，从redis中查询学生信息
        String userInfoStr = redisTemplate.opsForValue().get("student:login"+token);

        //如果redis查不到数据，查找教师信息
        if (StrUtil.isEmpty(userInfoStr)){
            userInfoStr = redisTemplate.opsForValue().get("teacher:login"+token);
        }else{
            //5.如果redis查询到用户信息,将用户信息放入ThreadLocal
            AuthContextUtil.setStudent(JSON.parseObject(userInfoStr, Student.class));

            //6.把redis用户信息更新过期时间
            redisTemplate.expire("student:login"+token,30, TimeUnit.MINUTES);

            //7.放行
            return true;
        }

        //如果redis查不到数据，查找管理员信息
        if (StrUtil.isEmpty(userInfoStr)){
            userInfoStr = redisTemplate.opsForValue().get("admin:login"+token);
        }else{
            //5.如果redis查询到用户信息,将用户信息放入ThreadLocal
            AuthContextUtil.setTeacher(JSON.parseObject(userInfoStr, Teacher.class));

            //6.把redis用户信息更新过期时间
            redisTemplate.expire("teacher:login"+token,30, TimeUnit.MINUTES);

            //7.放行
            return true;
        }

        //如果redis还是查不到数据，返回false
        if (StrUtil.isEmpty(userInfoStr)){
            return false;
        }else{
            //5.如果redis查询到用户信息,将用户信息放入ThreadLocal
            AuthContextUtil.setAdmin(JSON.parseObject(userInfoStr, Admin.class));

            //6.把redis用户信息更新过期时间
            redisTemplate.expire("admin:login"+token,30, TimeUnit.MINUTES);

            //7.放行
            return true;
        }


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception { //全部结束后拦截
        //ThreadLocal清除
        AuthContextUtil.removeAdmin();
        AuthContextUtil.removeStudent();
        AuthContextUtil.removeTeacher();
    }

    //无响应工具类，响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
