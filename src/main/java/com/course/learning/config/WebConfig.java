package com.course.learning.config;

import com.course.learning.common.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置：注册 JWT 拦截器
 * 放行：用户登录/注册/微信登录、课程列表与详情、章节列表等公开接口
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/register",
                        "/api/user/wx-login",
                        "/api/course/list",
                        "/api/course/*",
                        "/api/chapter/list/**",
                        "/api/class/list"
                );
    }
}
