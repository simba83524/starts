package com.simba.auth.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simba.system.entity.*;
import com.simba.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenjun
 * @date 2021-05-18
 * @time 11:50
 * @Description: 用户认证
 */

@Slf4j
@Component
public class StartsUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (ObjectUtil.isEmpty(user)) {
            throw new UsernameNotFoundException("用户名或密码错误，请联系管理员！");
        }
        //根据userid查询roleid
        List<UserRole> urlist = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id",user.getId()));
        if(ObjectUtil.isNotEmpty(urlist)){
            List<Integer> roleids = new ArrayList<>();
            urlist.forEach(ur->{
                roleids.add(ur.getRoleId());
            });
            user.setRoles(roleids.toArray(new Integer[roleids.size()]));
        }
        //根据roleid集合查询role
        List<Role> rlist = roleService.listByIds(Arrays.asList(user.getRoles()));
        if(ObjectUtil.isNotEmpty(rlist)){
            List<String> roles = new ArrayList<>();
            rlist.forEach(role -> {
                roles.add(role.getCode());
            });
            user.setRoleList(roles.toArray(new String[roles.size()]));
        }
        //根据roleid集合查询到menuid集合
        //List<RoleMenu> roleMenuList = roleMenuService.listByIds(Arrays.asList(user.getRoles()));
        List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().in("role_id",Arrays.asList(user.getRoles())));
        log.debug("传入参数：{}，查询出来的结果：{}",user.getRoles(),roleMenuList);
        List<Integer> menuids = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(roleMenuList)){
            roleMenuList.forEach(roleMenu -> {
                menuids.add(roleMenu.getMenuId());
            });
        }
        log.debug("查询menuids对象为:{}",menuids);
        //根据menuid集合查询到menu表中的permissions
        if(ObjectUtil.isNotEmpty(menuids)){
            List<Menu> menuList = menuService.listByIds(menuids);
            List<String> permissions = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(menuList)){
                menuList.forEach(menu -> {
                    String perm = menu.getPerms();
                    if(StrUtil.isNotEmpty(perm)){
                        permissions.add(perm);
                    }
                });
            }
            user.setPermissions(permissions.toArray(new String[permissions.size()]));
        }
        log.info("loadUserByUsername用户信息：{}", user);
        if (!user.isEnabled()) {
            throw new DisabledException("该账户已被禁用，请联系管理员！");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定，请联系管理员!");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期，请联系管理员!");
        } else if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录!");
        }
        return user;
    }
}
