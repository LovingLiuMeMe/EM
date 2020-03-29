package cn.lovingliu.component;

import cn.lovingliu.constant.UserType;
import cn.lovingliu.mapper.UserMapper;
import cn.lovingliu.pojo.User;
import cn.lovingliu.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author：LovingLiu
 * @Description: 系统启动时自动创建超级管理员用户
 * @Date：Created in 2020-02-07
 */
@Component
@Slf4j
public class InitUser implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            log.debug("============== 初始化Header器材负责人 ==============");
            List<User> userList = userMapper.selectByType(UserType.HEADER);
            if(userList.size() < 1){
                User header = new User();
                header.setName("器材负责人");
                header.setPassword(MD5Util.MD5EncodeUtf8("123456"));
                header.setTelephone("13000000000");
                header.setNumber("H01");
                header.setType(UserType.HEADER);
                header.setGender(0);
                header.setCollege("信息工程学院");
                header.setMajor("计算机科学与技术");
                header.setCreatedTime(new Date());
                header.setUpdatedTime(new Date());
                userMapper.insertSelective(header);
            }

            log.debug("============== 初始化Manager器材管理人员 ==============");
            userList = userMapper.selectByType(UserType.MANAGER);
            if(userList.size() < 1){
                User manager = new User();
                manager.setName("器材管理员");
                manager.setPassword(MD5Util.MD5EncodeUtf8("123456"));
                manager.setTelephone("13000000001");
                manager.setNumber("M01");
                manager.setType(UserType.MANAGER);
                manager.setGender(0);
                manager.setCollege("信息工程学院");
                manager.setMajor("计算机科学与技术");
                manager.setCreatedTime(new Date());
                manager.setUpdatedTime(new Date());
                userMapper.insertSelective(manager);
            }

            log.debug("============== 初始化Teacher教师 ==============");
            userList = userMapper.selectByType(UserType.TEACHER);
            if(userList.size() < 1){
                User teacher = new User();
                teacher.setName("教师");
                teacher.setPassword(MD5Util.MD5EncodeUtf8("123456"));
                teacher.setTelephone("13000000002");
                teacher.setNumber("T01");
                teacher.setType(UserType.TEACHER);
                teacher.setGender(0);
                teacher.setCollege("信息工程学院");
                teacher.setMajor("计算机科学与技术");
                teacher.setCreatedTime(new Date());
                teacher.setUpdatedTime(new Date());
                userMapper.insertSelective(teacher);
            }

            log.debug("============== 初始化Student学生 ==============");
            userList = userMapper.selectByType(UserType.STUDENT);
            if(userList.size() < 1){
                User student = new User();
                student.setName("学生");
                student.setPassword(MD5Util.MD5EncodeUtf8("123456"));
                student.setTelephone("13000000003");
                student.setNumber("S01");
                student.setType(UserType.STUDENT);
                student.setGender(0);
                student.setCollege("信息工程学院");
                student.setMajor("计算机科学与技术");
                student.setCreatedTime(new Date());
                student.setUpdatedTime(new Date());
                userMapper.insertSelective(student);
            }
        }).start();
    }
}
