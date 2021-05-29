package com.simba.auth.component;

import cn.hutool.json.JSONUtil;
import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.system.log.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint
{

    @Log(title = "认证异常",businessType = BusinessType.OTHER)
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String errmsg = e.getMessage().split(":")[0];
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String body = JSONUtil.toJsonStr(R.unAuthentication(errmsg,e.getClass()));
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,body);
        log.info("======进入{}，拦截异常信息：{}===={}",this.getClass().getName(),e.getClass().getSimpleName(),e.getLocalizedMessage());
        PrintWriter printWriter = response.getWriter();
        printWriter.print(body);
        printWriter.flush();
        printWriter.close();
    }
}