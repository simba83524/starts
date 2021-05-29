package com.simba.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenjun
 * @date 2021-05-16
 * @time 21:33
 * @Description: Mybatis-Plus配置类
 */
@Configuration
@MapperScan("com.simba.system.mapper")
public class MybatisPlusConfig {
}
