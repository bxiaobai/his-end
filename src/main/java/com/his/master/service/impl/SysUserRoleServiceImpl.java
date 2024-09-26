package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.SysUserRole;
import com.his.master.service.SysUserRoleService;
import com.his.master.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2024-06-24 17:37:32
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

    @Override
    public List<Long> listRoleUserById(Long userId) {
        return lambdaQuery()
                .eq(SysUserRole::getUserId,userId)
                .list()
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
    }
}




