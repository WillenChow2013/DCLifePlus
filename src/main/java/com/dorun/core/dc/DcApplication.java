package com.dorun.core.dc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dorun.core.dc.*")
@MapperScan("com.dorun.core.dc.mapper")
public class DcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcApplication.class, args);
    }

}
