package com.simba.common.config;

import com.simba.common.filter.GlobalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.DispatcherType;

/**
 * @author chenjun
 * @date 2021-05-23
 * @time 12:52
 * @Description: starts配置
 */

@Configuration
public class XssConfiguration {

    @Bean
    public FilterRegistrationBean globalFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new GlobalFilter());
        registration.addUrlPatterns("/*");
        registration.setName("startsGlobalFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }
}
