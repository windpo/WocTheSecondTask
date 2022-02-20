package com.xuyang.wocexample.config;


import com.xuyang.wocexample.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//springweb拦截器配置文件
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login.html")
                .excludePathPatterns("/regist.html")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/regist");
    }
}
