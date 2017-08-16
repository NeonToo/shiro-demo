package com.sap.csc.web;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sap.csc.web.interceptor.CustomizedHandlerInterceptor;

@Configuration
@CrossOrigin
public class WebConfig extends WebMvcConfigurerAdapter {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = false)
	protected List<CustomizedHandlerInterceptor> customizedHandlerInterceptors;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (!CollectionUtils.isEmpty(this.customizedHandlerInterceptors)) {
			for (final CustomizedHandlerInterceptor customizedHandlerInterceptor : this.customizedHandlerInterceptors) {
				registry.addInterceptor(customizedHandlerInterceptor);
			}
		}
		super.addInterceptors(registry);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public FilterRegistrationBean corsFilterRegistrationBean() {
		FilterRegistrationBean bean = new FilterRegistrationBean(corsFilter());
		
		bean.setOrder(0); // must set or this bean won't work
		return bean;
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		source.registerCorsConfiguration("/**", config);
//
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//
//		return bean;
	}
	
	@Bean(name = "corsFilter")
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
	}

}
