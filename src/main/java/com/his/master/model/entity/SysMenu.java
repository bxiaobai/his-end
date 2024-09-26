package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 菜单状态（0 显示 1 隐藏）
     */
    private Integer visible;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String icon;

    @TableField(exist = false)
    private List<SysMenu> routes = new ArrayList<>();

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}