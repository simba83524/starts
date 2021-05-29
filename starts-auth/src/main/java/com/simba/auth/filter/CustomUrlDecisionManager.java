package com.simba.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;

/**
 * 权限控制
 * 判断用户角色
 */
@Slf4j
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("开始判断请求url所需要的角色在当前用户是否拥有?");
        log.info("授权authentication：{}",authentication.toString());
        log.info("传入object：{}",object.toString());
        log.info("ConfigAttribute：{}",configAttributes.toString());
        for (ConfigAttribute configAttribute : configAttributes) {
            //当前url所需要的角色
            String needRole = configAttribute.getAttribute();
            //判断角色是否登录即可访问，此角色在CustomFilter中设置
            log.info("请求url所需要的的角色为：{}", needRole);
            log.info("开始判断用户角色是否为url所需要的角色?");
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                log.info("共"+authorities.size()+"条角色，开始匹配角色："+authority.getAuthority());
                if(authority.getAuthority().equals(needRole)){
                    log.info("匹配成功直接返回");
                    return;
                }
            }
            /*log.info("============如果没有匹配上，开始判断用户角色是否为ROLE_LOGIN角色============");
            if("ROLE_login".equals(needRole)){
                log.info("判断登录用户只要登录即可访问该资源");
                if(authentication instanceof AnonymousAuthenticationToken){
                    log.info("判断该请求尚未登录，不存在合法用户信息" + authentication.getAuthorities().toString());
                    throw new AccessDeniedException("尚未登录，请登录！");
                } else {
                    log.info("---------判断为非匿名直接返回----------");
                    return;
                }
            }*/
        }
        throw new AccessDeniedException("权限不足，请联系管理员授权！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
