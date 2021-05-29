package com.simba.api;

import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.system.log.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenjun
 * @date 2021-05-21
 * @time 16:40
 * @Description: 统一异常处理
 */

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @Log(title = "异常日志",businessType = BusinessType.OTHER)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R globalException(HttpServletRequest request, Exception e) {
        log.info("异常StackTrace：{}",e.fillInStackTrace());
        //e.printStackTrace();
        log.info("再看看：{}",e.toString());
        return R.fail(e.getMessage(),e.getClass());
    }

}
