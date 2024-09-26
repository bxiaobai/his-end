package com.his.master.service;

import com.his.master.model.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service
 * @createDate 2024-06-24 17:37:32
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    //获取用户对应角色
    List<Long> listRoleUserById(Long userId);
}
