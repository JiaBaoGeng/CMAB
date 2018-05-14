package cn.jbg.cmab;

import cn.jbg.cmab.configurations.web.ImageServlet;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbg on 2018/4/22.
 */
@SpringCloudApplication
public class CMABWebApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        new SpringApplicationBuilder(CMABWebApplication.class).web(true).run(args);
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

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CMABWebApplication.class);
    }
}
