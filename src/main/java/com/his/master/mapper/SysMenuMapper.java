package com.his.master.mapper;

import com.his.master.model.entity.*;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2024-08-23 17:54:39
* @Entity com.his.master.model.entity.SysMenu
*/
public interface SysMenuMapper extends BaseMapperX<SysMenu> {

   default List<SysMenu> getMenuByUserId(Long userId){
       MPJLambdaWrapperX<SysMenu> mpjLambdaWrapperX = new MPJLambdaWrapperX<>();
       mpjLambdaWrapperX.selectAll(SysMenu.class)
               .leftJoin(SysRoleMenu.class,SysRoleMenu::getMenuId,SysMenu::getId)
               .leftJoin(SysRole.class,SysRole::getRoleId,SysRoleMenu::getRoleId)
               .leftJoin(SysUserRole.class,SysUserRole::getRoleId,SysRole::getRoleId)
               .leftJoin(SysUser.class,SysUser::getUserId,SysUserRole::getUserId)
               .eq(SysUser::getUserId,userId)
               .eq(SysMenu::getVisible,"0")
               .eq(SysRole::getStatus,"0");
       return selectList(mpjLambdaWrapperX);
   }
}




