package com.simba.auth.component;

import cn.hutool.json.JSONUtil;
import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.system.log.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Log(title = "授权异常",businessType = BusinessType.OTHER)
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        String errmsg = e.getMessage().split(":")[0];
        String body= JSONUtil.toJsonStr(R.unAuthorization(errmsg,e.getClass()));
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        log.info("======进入{}，拦截异常：{}==={}",this.getClass().getName(),e.getClass().getSimpleName(),e.getLocalizedMessage());
        PrintWriter printWriter = response.getWriter();
        printWriter.print(body);
        printWriter.flush();
        printWriter.close();
    }
}
