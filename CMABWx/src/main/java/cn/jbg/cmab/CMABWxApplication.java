package cn.jbg.cmab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by jbg on 2018/4/22.
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableCircuitBreaker
@ServletComponentScan
//@MapperScan("cn.jbg.cmab.**.dao")
public class CMABWxApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(CMABWxApplication.class);
        springApplication.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CMABWxApplication.class);
    }

}
