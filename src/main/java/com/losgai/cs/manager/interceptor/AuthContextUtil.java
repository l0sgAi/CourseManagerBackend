package com.losgai.cs.manager.interceptor;

import com.losgai.cs.manager.entity.Admin;
import com.losgai.cs.manager.entity.Student;
import com.losgai.cs.manager.entity.Teacher;

public class AuthContextUtil {
    //TODO 创建学生ThreadLocal对象
    private static final ThreadLocal<Student> threadLocalStu = new ThreadLocal<>();

    //添加数据
    public static void setStudent(Student student) {
        threadLocalStu.set(student);
    }

    //获取数据
    public static Student getStudent() {
        return threadLocalStu.get();
    }

    //删除数据
    public static void removeStudent() {
        threadLocalStu.remove();
    }

    //TODO 创建教师ThreadLocal对象
    private static final ThreadLocal<Teacher> threadLocalTea = new ThreadLocal<>();


    //添加数据
    public static void setTeacher(Teacher teacher) {
        threadLocalTea.set(teacher);
    }

    //获取数据
    public static Teacher getTeacher() {
        return threadLocalTea.get();
    }

    //删除数据
    public static void removeTeacher() {
        threadLocalTea.remove();
    }

    //TODO 创建管理员ThreadLocal对象
    private static final ThreadLocal<Admin> threadLocalAd = new ThreadLocal<>();

    //添加数据
    public static void setAdmin(Admin admin) {
        threadLocalAd.set(admin);
    }

    //获取数据
    public static Admin getAdmin() {
        return threadLocalAd.get();
    }

    //删除数据
    public static void removeAdmin() {
        threadLocalAd.remove();
    }

}