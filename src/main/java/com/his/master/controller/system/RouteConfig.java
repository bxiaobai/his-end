package com.his.master.controller.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteConfig {
    private String path;
    private Boolean layout;
    private String name;
    private String icon;
    private String access; // 权限标识，例如角色或权限
    private List<Map<String, Object>> routes;
    private String component; // 组件路径
    private String redirect; // 重定向路径

    // 省略 getter 和 setter 方法
}