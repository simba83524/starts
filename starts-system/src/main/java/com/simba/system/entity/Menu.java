package com.simba.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.List;

import com.simba.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenjun
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value="Menu对象", description="")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "父菜单IDS")
    private String parentIds;

    /**
     * 父菜单名称
     */
    @TableField(exist = false)
    private String parentName;

    @ApiModelProperty(value = "应用编号")
    private Integer applicationId;

    @ApiModelProperty(value = "菜单类型 ")
    private String type;

    @ApiModelProperty(value = "前端URL")
    private String path;

    @ApiModelProperty(value = "VUE页面")
    private String component;

    @ApiModelProperty(value = "菜单权限标识")
    private String perms;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "路由缓存")
    private String noCache;

    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private String status;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<Role> roles;


}
