package cn.jbg.cmab;

import cn.jbg.cmab.common.mybatis.PageInterceptor;
import cn.jbg.cmab.common.web.ImageServlet;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbg on 2018/4/22.
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableCircuitBreaker
@ServletComponentScan
@MapperScan("cn.jbg.cmab.backend.**.dao")
//@EnableAutoConfiguration
public class CMABBackendApplication extends SpringBootServletInitializer {
    /**
     * DataSource配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    /**
     * 分页拦截器设置
     *
     * @return
     */
    @Bean(name = "paginationInterceptor")
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setDbType("mysql");
        return pageInterceptor;
    }


    /**
     * 提供SqlSeesion
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("paginationInterceptor") PageInterceptor paginationInterceptor)
            throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:cn/jbg/cmab/backend/**/dao/*.xml"));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{paginationInterceptor});
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置 拦截图片请求的servlet
     */
    @Bean
    public ServletRegistrationBean getImageServlet() {
        ImageServlet imageServlet = new ImageServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(imageServlet);

        List<String> urlMappings = new ArrayList<String>();
        urlMappings.add("/images/*"); //请求图片
        urlMappings.add("/uploadImage/*");  //上传图片
        registrationBean.setUrlMappings(urlMappings);

        return registrationBean;
    }
    /*@Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }*/

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CMABBackendApplication.class, args);
    }
}
