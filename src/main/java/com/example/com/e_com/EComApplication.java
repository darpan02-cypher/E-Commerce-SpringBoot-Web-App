package com.example.com.e_com;

import com.example.com.e_com.filter.RequestIdFilter;
import com.example.com.e_com.logintelligence.config.LogIntelligenceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(LogIntelligenceProperties.class)
public class EComApplication {

	public static void main(String[] args) {
		SpringApplication.run(EComApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<RequestIdFilter> requestIdFilter() {
		FilterRegistrationBean<RequestIdFilter> registration = new FilterRegistrationBean<>(new RequestIdFilter());
		registration.setOrder(1);
		registration.addUrlPatterns("/*");
		return registration;
	}
}
