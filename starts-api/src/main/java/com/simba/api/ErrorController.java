package com.simba.api;

import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.system.log.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author chenjun
 * @date 2021-05-21
 * @time 16:27
 * @Description: 异常处理
 */
@Slf4j
@RestController
public class ErrorController extends AbstractErrorController {

    private final static String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @Log(title = "错误日志",businessType = BusinessType.OTHER)
    @RequestMapping(ERROR_PATH)
    public R error(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION,ErrorAttributeOptions.Include.MESSAGE,ErrorAttributeOptions.Include.STACK_TRACE,ErrorAttributeOptions.Include.BINDING_ERRORS));
        //获取错误信息
        String code = errorAttributes.get("status").toString();
        String message = errorAttributes.get("message").toString();
        String trace = errorAttributes.get("trace").toString();
        log.info("attributes：{}, trace：{}",errorAttributes,trace);
        return R.fail(message,errorAttributes);
    }

}
