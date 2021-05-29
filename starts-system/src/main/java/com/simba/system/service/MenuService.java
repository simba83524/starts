package com.simba.system.service;

import com.simba.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simba.system.vo.MenuVo;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenjun
 * @since 2021-05-18
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据角色获取菜单列表
     * Param url
     * @return
     */
    List<Menu> getMenusWithRoleByUrl(String url);
    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> selectMenuListByRoleId(Integer roleId);

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单编号
     */
    List<Integer> selectMenusByRoleId(Integer roleId);

    /**
     * 构建树
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<Menu> buildTree(List<Menu> list, int parentId);

    /**
     * 菜单转换为路由
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<MenuVo> buildMenus(List<Menu> menus);
}
