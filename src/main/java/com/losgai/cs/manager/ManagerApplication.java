package com.losgai.cs.manager;

import com.losgai.cs.manager.properties.MinioProperties;
import com.losgai.cs.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //开启定时任务功能
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
public class ManagerApplication { //manager启动类
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
