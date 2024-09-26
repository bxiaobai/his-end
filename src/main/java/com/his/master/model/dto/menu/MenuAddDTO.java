package com.his.master.model.dto.menu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class MenuAddDTO {
    /**
     * 主键ID
     */
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

}
