package com.laolang.zuke.framework.web.config;

import com.laolang.zuke.framework.web.interceptor.ControllerLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ZukeWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerLogInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 允许跨域的路径
                .addMapping("/**")
                // 允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许 cookie
                .allowCredentials(true)
                // 允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 允许设置的 Header 属性
                .allowedHeaders("*")
                // 跨域时间
                .maxAge(3600);
    }
}
