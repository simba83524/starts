package com.simba.auth.exception;

import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.system.log.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenjun
 * @date 2021-05-19
 * @time 19:43
 * @Description: 认证失败统一格式化
 */
@Slf4j
@ControllerAdvice
public class Oauth2ExceptionHandler {

    @Log(title = "OAuth2异常",businessType = BusinessType.OTHER)
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public R handleOauth2(OAuth2Exception e){
        String errorCode = e.getOAuth2ErrorCode();
        String errmsg = e.getMessage().split(":")[0];
        log.info("======进入{}，打印e对象{}，打印errorCode：{}，打印报错信息：{}",this.getClass(),e.getClass(),e.getOAuth2ErrorCode(),e.getMessage().split(":")[0]);
        return R.unOAuth2(errmsg,e.getClass());
    }

}
