package com.simba.system.log.annotation;

import com.simba.common.enums.BusinessType;
import com.simba.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @author chenjun
 * @date 2021-05-22
 * @time 12:05
 * @Description: 日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;
}
