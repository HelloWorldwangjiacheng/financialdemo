package com.imooc.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @Author w1586
 * @Date 2020/3/11 15:45
 * @Cersion 1.0
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.imooc.entity"})
public class ManagerApp {
    public static void main(String[] args){
        SpringApplication.run(ManagerApp.class, args);
    }
}
