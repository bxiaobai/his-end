package com.his.master.model.vo;


import com.his.master.utils.StringUtils;
import lombok.Data;

/**
 * 路由显示信息
 * 
 * @author ruoyi
 */
@Data
@SuppressWarnings("all")
public class MetaVo
{
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String locale;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    private boolean requiresAuth;

    /**
     * 内链地址（http(s)://开头）
     */
    private Integer order;


    private boolean hideInMenu;

    public MetaVo() {
    }

    public MetaVo(String locale, String icon, boolean requiresAuth, Integer orde , boolean hideInMenu) {
        this.locale = locale;
        this.requiresAuth = requiresAuth;
        this.icon = icon;
        this.order = order;
        this.hideInMenu = hideInMenu;
    }
}
