package com.simba.api;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simba.common.enums.BusinessType;
import com.simba.common.response.R;
import com.simba.common.utils.SecurityUtil;
import com.simba.common.utils.StrUtil;
import com.simba.system.entity.*;
import com.simba.system.log.annotation.Log;
import com.simba.system.service.*;
import com.simba.system.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenjun
 * @date 2021-05-26
 * @time 16:06
 * @Description: Menu接口类
 */
@RestController
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    private QueryWrapper<Menu> getQueryWrapper(Menu menu) {
        return new QueryWrapper<Menu>().like(StrUtil.isNotBlank(menu.getName()), "name", menu.getName()).eq(StrUtil.isNotBlank(menu.getStatus()), "status", menu.getStatus()).orderByAsc("sort")
                .between(StrUtil.isNotBlank(menu.getBeginTime()) && StrUtil.isNotBlank(menu.getEndTime()), "create_time", menu.getBeginTime(), menu.getEndTime());
    }

    @PreAuthorize("@cp.hasPerm('menu_view')")
    @GetMapping("/list")
    public R list(Menu menu) {
        List<Menu> menuList = menuService.list(getQueryWrapper(menu));
        if (menuList.size() > 0) {
            for (Menu menu1 : menuList) {
                if (StrUtil.isNotBlank(menu.getName()) || StrUtil.isNotBlank(menu.getStatus())) {
                    menu1.setParentId(0);
                }
            }
        }
        return R.ok(menuList);
    }

    @GetMapping
    public R getMenus() {
        log.info("进入：{}",this.getClass());
        List<Application> applications = applicationService.list(new QueryWrapper<Application>().orderByAsc("sort"));
        Set<Menu> menuSet = new HashSet<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User user = userService.getOne(new QueryWrapper<User>().eq("user_name",username));
        List<Integer> rids = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(user)){
            List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id",user.getId()));
            userRoleList.forEach(userRole -> {
                rids.add(userRole.getRoleId());
            });
        }else {
            throw new AccountExpiredException("该账号已过期，请联系管理员!");
        }
        rids.forEach(roleId -> menuSet.addAll(menuService.selectMenuListByRoleId(roleId)));
        List<Menu> menuList = menuSet.stream().sorted(Comparator.comparingInt(Menu::getSort)).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("applications", applications);
        map.put("menus", menuService.buildMenus(menuService.buildTree(menuList, 0)));
        return R.ok(map);
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(menuService.getById(id));
    }

    @Log(title = "菜单新增",businessType = BusinessType.INSERT)
    @PreAuthorize("@cp.hasPerm('menu_add')")
    @PostMapping("/save")
    public R save(@RequestBody Menu menu) {
        menuService.save(menu);
        return R.ok();
    }

    @Log(title = "菜单修改",businessType = BusinessType.UPDATE)
    @PreAuthorize("@cp.hasPerm('menu_update')")
    @PutMapping("/update")
    public R update(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return R.ok();
    }

    @Log(title = "菜单删除",businessType = BusinessType.DELETE)
    @PreAuthorize("@cp.hasPerm('menu_del')")
    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public R remove(@PathVariable("id") Integer id) {
        if (menuService.count(new QueryWrapper<Menu>().eq("parent_id", id)) > 0) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (roleMenuService.count(new QueryWrapper<RoleMenu>().eq("menu_id", id)) > 0) {
            return R.fail("菜单已分配,不允许删除");
        }
        menuService.removeById(id);
        return R.ok();
    }

    @Log(title = "菜单状态更改",businessType = BusinessType.UPDATE)
    @PreAuthorize("@cp.hasPerm('menu_edit')")
    @GetMapping("/changeStatus")
    public R changeStatus(Menu menu) {
        menuService.updateById(menu);
        return R.ok();
    }

    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTree")
    @ResponseBody
    public R menuTree(@RequestParam(required = false) String applicationIds) {
        List<Menu> menuList = menuService.list(new QueryWrapper<Menu>().in(StrUtil.isNotBlank(applicationIds), "application_id", applicationIds).eq("status", "0").orderByAsc("sort"));
        return R.ok(menuList);
    }

    /**
     * 加载角色菜单列表树
     */
    @GetMapping("/roleMenuTree/{roleId}")
    public R roleMenuTree(@PathVariable Integer roleId) {
        List<Menu> menuList = menuService.list(new QueryWrapper<Menu>().eq("status", "0").orderByAsc("sort"));
        return R.ok(ResultVo.builder().result(menuList).extend(menuService.selectMenusByRoleId(roleId)).build());
    }
}
