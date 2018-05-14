package cn.jbg.cmab.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@Order(1)
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	@Order(1)
	public FilterRegistrationBean getcharacterEncodingFilter(){
		CharacterEncodingFilter characterEncodingFilter=new CharacterEncodingFilter();
		
		Map<String,String> initParameters = new HashMap<>();
		initParameters.put("encoding", "UTF-8");
		initParameters.put("forceEncoding", "true");

		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(characterEncodingFilter);
		registrationBean.setInitParameters(initParameters);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}

}
