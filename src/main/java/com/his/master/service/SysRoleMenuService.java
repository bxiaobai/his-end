package com.his.master.service;

import com.his.master.model.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service
* @createDate 2024-06-24 17:37:32
*/
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    //查询角色对应菜单
    public List<Long> selectRoleMenuByRoleId(Long roleId);
}
