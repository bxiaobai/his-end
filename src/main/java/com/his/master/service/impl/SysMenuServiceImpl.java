package com.his.master.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.mapper.SysMenuMapper;
import com.his.master.model.dto.menu.MenuAddDTO;
import com.his.master.model.dto.menu.MenuQueryDTO;
import com.his.master.model.entity.SysMenu;
import com.his.master.model.entity.SysUser;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_menu】的数据库操作Service实现
 * @createDate 2024-08-23 17:54:39
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
        implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getAllMenu(MenuQueryDTO menuQueryDTO) {
        LambdaQueryWrapperX<SysMenu> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.likeIfPresent(SysMenu::getName, menuQueryDTO.getName())
                .eqIfPresent(SysMenu::getVisible, menuQueryDTO.getVisible());
        return list(lambdaQueryWrapperX);
    }

    @Override
    public List<SysMenu> getMenuByUserId(Long userId) {
        if (SysUser.isAdmin(userId)) {
            LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SysMenu::getVisible , "0");
            return list(lambdaQueryWrapper);
        } else {
            return sysMenuMapper.getMenuByUserId(userId);
        }
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menuList) {
        List<SysMenu> list = new ArrayList<>();
        for (SysMenu sysMenu : menuList) {
            if (sysMenu.getParentId() == 0) {
                list.add(findChildren(sysMenu, menuList));
            }
        }
        return list;
    }

    private SysMenu findChildren(SysMenu sysMenu, List<SysMenu> menuList) {
        //如果父id等于子菜单的id放入
        for (SysMenu menu : menuList) {
            if (menu.getParentId().equals(sysMenu.getId())) {
                sysMenu.getRoutes().add(findChildren(menu, menuList));
            }
        }
        return sysMenu;
    }

    @Override
    public boolean checkMenuNameUnique(MenuAddDTO sysMenu) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getName, sysMenu.getName());
        SysMenu one = getOne(queryWrapper);
        return one == null || sysMenu.getId().equals(one.getId());
    }

    @Override
    public boolean addMenu(MenuAddDTO menuAddDTO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(menuAddDTO, sysMenu);
        return save(sysMenu);
    }


    @Override
    public boolean hasChildByMenuId(Long menuId) {
        return sysMenuMapper.selectCount(SysMenu::getParentId, menuId) > 0;
    }

    @Override
    public boolean deleteMenu(Long menuId) {
        return removeById(menuId);
    }

    @Override
    public boolean updateMenuById(MenuAddDTO menu) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(menu, sysMenu);
        return  updateById(sysMenu);
    }

    @Override
    public SysMenu selectMenuById(Long id) {
        return getById(id);
    }
}




