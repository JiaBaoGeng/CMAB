package cn.jbg.cmab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by jbg on 2018/4/22.
 */

@EnableEurekaServer
@SpringBootApplication
public class CMABCenterApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(CMABCenterApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CMABCenterApplication.class);
    }
}
