package com.his.master.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.constant.UserConstants;
import com.his.master.model.dto.role.RoleAddDTO;
import com.his.master.model.dto.role.RoleQueryDTO;
import com.his.master.model.entity.SysRole;
import com.his.master.model.entity.SysRoleMenu;
import com.his.master.service.SysMenuService;
import com.his.master.service.SysRoleMenuService;
import com.his.master.service.SysRoleService;
import com.his.master.mapper.SysRoleMapper;
import com.his.master.utils.PageResult;
import com.his.master.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2024-06-24 15:08:41
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysRoleMapper sysRoleMapper;
    /**
     * 角色列表
     *
     * @param roleQueryDTO
     * @return
     */
    @Override
    public PageResult<SysRole> listRole(RoleQueryDTO roleQueryDTO) {
        return sysRoleMapper.selectPageByRoleQuery(roleQueryDTO);
    }

    /**
     * 获取角色详情
     * @param id
     * @return
     */
    @Override
    public SysRole getRoleById(Long id) {
        List<Long> longs = sysRoleMenuService.selectRoleMenuByRoleId(id);
        SysRole byId = getById(id);
        byId.setMenuIds(longs);
        return byId;
    }

    /**
     * 添加角色
     * @param roleAddDTO
     * @return
     */
    @Override
    public Boolean addRole(RoleAddDTO roleAddDTO) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(roleAddDTO,sysRole);
        //添加角色
        this.save(sysRole);
        //添加角色关联
        return addRoleMenu(sysRole.getRoleId(),roleAddDTO.getMenuIds());
    }

    @Override
    public Boolean updateRole(RoleAddDTO roleAddDTO) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(roleAddDTO,sysRole);
        //删除角色的菜单关联
        deleteRoleMenu(sysRole.getRoleId());
        //添加角色和菜单的关联
        addRoleMenu(sysRole.getRoleId(),roleAddDTO.getMenuIds());
        return updateById(sysRole);
    }

    @Override
    public Boolean deleteRole(Long id) {
        //删除角色菜单关联
        deleteRoleMenu(id);
        //删除角色
        return removeById(id);
    }

    @Override
    public Boolean addRoleMenu(Long roleId, List<Long> menuIds) {
        List<SysRoleMenu> list = new ArrayList<>();
        for (Long menuId : menuIds){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            list.add(sysRoleMenu);
        }
        return sysRoleMenuService.saveBatch(list);
    }

    @Override
    public Boolean deleteRoleMenu(Long roleId) {
        return sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId,roleId));
    }

    /**
     * 判断权限字符是否唯一
     * @return
     */
    @Override
    public Boolean checkRoleKeyUnique(RoleAddDTO roleAddDTO) {
        Long userId = StringUtils.isNull(roleAddDTO.getRoleId()) ? -1L : roleAddDTO.getRoleId();
        SysRole info = lambdaQuery().eq(SysRole::getRoleKey, roleAddDTO.getRoleKey()).one();
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override

    public Boolean checkRoleNameUnique(RoleAddDTO roleAddDTO) {
        Long userId = StringUtils.isNull(roleAddDTO.getRoleId()) ? -1L : roleAddDTO.getRoleId();
        SysRole info = lambdaQuery().eq(SysRole::getRoleName, roleAddDTO.getRoleName()).one();
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    private LambdaQueryWrapper<SysRole> getQueryWrapper(RoleQueryDTO roleQueryDTO) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (roleQueryDTO.getRoleName() != null && !roleQueryDTO.getRoleName().isEmpty()){
            queryWrapper.eq(SysRole::getRoleName, roleQueryDTO.getRoleName());
        }
        if (roleQueryDTO.getStatus() != null){
            queryWrapper.eq(SysRole::getStatus, roleQueryDTO.getStatus());
        }
        return queryWrapper;
    }
}




