package com.his.master.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.his.master.model.dto.role.RoleQueryDTO;
import com.his.master.model.entity.SysRole;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.mybatis.PageParam;
import com.his.master.utils.PageResult;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2024-06-24 15:08:41
* @Entity com.his.master.model.entity.SysRole
*/
@Mapper
public interface SysRoleMapper extends BaseMapperX<SysRole> {
    default PageResult<SysRole> selectPageByRoleQuery(RoleQueryDTO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SysRole>()
                .likeIfPresent(SysRole::getRoleName,reqVO.getRoleName())
                .eqIfPresent(SysRole::getStatus,reqVO.getStatus()));
    }
}




