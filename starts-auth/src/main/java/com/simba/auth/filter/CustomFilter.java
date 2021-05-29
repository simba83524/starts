package com.simba.auth.filter;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simba.common.constant.AuthConstant;
import com.simba.system.entity.Menu;
import com.simba.system.entity.Role;
import com.simba.system.entity.RoleMenu;
import com.simba.system.service.MenuService;
import com.simba.system.service.RoleMenuService;
import com.simba.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求的url分析请求所需要的角色
 */
@Slf4j
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleService roleService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        Menu menu = menuService.getOne(new QueryWrapper<Menu>().eq("path",requestUrl));
        log.debug("进入CustomFilter,查询记录:{},请求的url：{}",menu,requestUrl);
        List<Integer> rids = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(menu)){
            List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("menu_id",menu.getId()));
            if(ObjectUtil.isNotEmpty(roleMenuList)){
                roleMenuList.forEach(roleMenu -> {
                    rids.add(roleMenu.getRoleId());
                });
            }
        }
        List<Role> roleList = roleService.listByIds(rids);
        List<String> rolecode = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(roleList)){
            roleList.forEach(role -> {
                rolecode.add(role.getCode());
            });
        }
        if(rolecode.size() > 0){
            String[] str = rolecode.stream().map(role -> AuthConstant.ROLE + role).toArray(String[]::new);
            log.debug("获取到用户权限为：{}",str);
            return SecurityConfig.createList(str);
        }
        log.debug("权限控制表中没有找到url：" + requestUrl + "匹配的角色,授权该url为ROLE_login登录即可访问！");
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
