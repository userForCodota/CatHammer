package com.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigurerPlus implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将static下的所有文件夹及相关子文件夹都添加进扫描路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
