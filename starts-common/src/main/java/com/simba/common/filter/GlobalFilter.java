package com.simba.common.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import com.simba.common.config.StartsConfig;
import com.simba.common.constant.ApiConstant;
import com.simba.common.request.GlobalRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.UrlPathHelper;

/**
 * 防止XSS攻击的过滤器
 *
 * @author chenjun
 */

@Slf4j
@Component
@Order(1)
public class GlobalFilter implements Filter {

    private StartsConfig startsConfig;

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //手动加载starts项目配置bean
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        startsConfig = (StartsConfig) ctx.getBean("startsConfig");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        req.setAttribute(ApiConstant.API_BEGIN_TIME, System.currentTimeMillis());
        String requestUri = urlPathHelper.getOriginatingRequestUri(req);
        log.debug("切记Filter的名字被占用的话会导致加载出现问题，GlobalFilter开始过滤[{}]",requestUri);
        req.setAttribute(ApiConstant.API_REQURL, requestUri);
        GlobalRequestWrapper xssRequest = new GlobalRequestWrapper(startsConfig.getXss(), req);
        chain.doFilter(xssRequest, response);
    }

}