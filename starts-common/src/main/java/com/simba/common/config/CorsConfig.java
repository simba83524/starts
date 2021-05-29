package com.simba.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author chenjun
 * @date 2021-05-21
 * @time 10:33
 * @Description: 跨域配置
 */

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 是否支持cookie跨域
        config.setAllowedOrigins(Arrays.asList("*")); // 允许的原始域
        config.setAllowedHeaders(Arrays.asList("*")); // 允许的头
        config.setAllowedMethods(Arrays.asList("*")); // 允许的方法
        config.setMaxAge(300l); // 缓存时间

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
