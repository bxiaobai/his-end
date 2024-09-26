package com.his.master.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.model.dto.role.RoleAddDTO;
import com.his.master.model.dto.role.RoleQueryDTO;
import com.his.master.model.entity.SysRole;
import com.his.master.utils.PageResult;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2024-06-24 15:08:41
*/
public interface SysRoleService extends IService<SysRole> {

    PageResult<SysRole> listRole(RoleQueryDTO roleQueryDTO);

    SysRole getRoleById(Long id);

    Boolean addRole(RoleAddDTO roleAddDTO);

    Boolean updateRole(RoleAddDTO roleAddDTO);

    Boolean deleteRole(Long id);

    //添加角色和菜单的关联
    Boolean addRoleMenu(Long roleId, List<Long> menuId);

    //删除角色和菜单的关联
    Boolean deleteRoleMenu(Long roleId);

    //判断角色名是否唯一
    Boolean checkRoleKeyUnique(RoleAddDTO roleKey);

    //判断权限字符是否唯一
    Boolean checkRoleNameUnique(RoleAddDTO roleName);

}
