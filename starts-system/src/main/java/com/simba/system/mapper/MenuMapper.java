package com.simba.system.mapper;

import com.simba.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenjun
 * @since 2021-05-18
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusWithRoleByUrl(String url);
    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> selectMenuListByRoleId(Integer roleId);
    /**
     * 通过角色编号查询菜单编号
     *
     * @param roleId 角色ID
     * @return 菜单编号列表
     */
    List<Integer> selectMenusByRoleId(Integer roleId);
}
