package com.his.master.service;

import com.his.master.model.dto.menu.MenuAddDTO;
import com.his.master.model.dto.menu.MenuQueryDTO;
import com.his.master.model.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.model.entity.SysRoleMenu;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2024-08-23 17:54:39
*/
public interface SysMenuService extends IService<SysMenu>  {

    /**
     * 获取全部菜单
     */
    List<SysMenu> getAllMenu(MenuQueryDTO menuQueryDTO);

    /**
     * 根据用户id获取菜单
     */
    List<SysMenu> getMenuByUserId(Long userId);

    /**
     * 构建菜单树
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menuList);


    /**
     * 判断菜单名字是否存在
     */
    boolean checkMenuNameUnique(MenuAddDTO sysMenu);

    boolean addMenu(MenuAddDTO menu);

    /**
     * 判断是否存在子菜单
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * 删除菜单
     */
    boolean deleteMenu(Long menuId);

    /**
     * 修改菜单
     */
    boolean updateMenuById(MenuAddDTO menu);

    /**
     * 查询菜单详情
     */
    SysMenu selectMenuById(Long id);

}
