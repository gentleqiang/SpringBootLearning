package com.gentleman.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gentleman_qiang
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.gentleman.model.mapper"})
public class BootMoreApplication{

    public static void main(String[] args) {
        SpringApplication.run(BootMoreApplication.class, args);
    }
}
