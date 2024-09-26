package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.SysRoleMenu;
import com.his.master.service.SysMenuService;
import com.his.master.mapper.SysRoleMenuMapper;
import com.his.master.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
 * @createDate 2024-06-24 17:37:32
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
        implements SysRoleMenuService {

    /**
     * 根据角色id查询菜单id
     * @param roleId
     * @return
     */
    @Override
    public List<Long> selectRoleMenuByRoleId(Long roleId) {
        return lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId)
                .list().stream().map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
    }
}




