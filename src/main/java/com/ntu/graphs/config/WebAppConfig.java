package com.ntu.graphs.config;

import com.ntu.graphs.access.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 设置拦截的路径、不拦截的路径、优先级等等
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new SessionInterceptor());
        interceptorRegistration.addPathPatterns("/admin-dash");
        interceptorRegistration.addPathPatterns("/admin-validate");
        interceptorRegistration.addPathPatterns("/upload");
        interceptorRegistration.addPathPatterns("/logout");
        interceptorRegistration.addPathPatterns("/update");
    }
}
