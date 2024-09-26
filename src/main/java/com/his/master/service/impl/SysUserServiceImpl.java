package   com.his.master.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.mapper.SysUserRoleMapper;
import com.his.master.model.dto.user.AddAndUpdateUserDTO;
import com.his.master.model.dto.user.UserQueryDTO;
import   com.his.master.model.entity.SysUser;
import com.his.master.model.entity.SysUserRole;
import com.his.master.service.SysUserRoleService;
import   com.his.master.service.SysUserService;
import   com.his.master.mapper.SysUserMapper;
import com.his.master.utils.StringUtils;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
* @createDate 2024-06-20 18:11:06
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;


    /**
     * 分页查询用户列表
     * @param sysUser
     * @return
     */
    @Override
    public Page<SysUser> listUser(UserQueryDTO sysUser) {
        //分页
        long pageSize = sysUser.getPageSize();
        long current = sysUser.getCurrent();
        return page(new Page<>(current,pageSize), getQueryWrapper(sysUser));
    }

    @Override
    public SysUser getUserById(Long id) {
        //获取用户对应角色
        List<Long> roles = sysUserRoleService.listRoleUserById(id);
        SysUser byId = getById(id);
        if (byId != null){
            byId.setRoleIds(roles);
        }
        return byId;
    }

    @Override
    public boolean addUser(AddAndUpdateUserDTO addAndUpdateUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(addAndUpdateUserDTO,sysUser);
        //加密用户密码
        sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        //删除用户和角色关联
        deleteUserRole(sysUser.getUserId());
        //添加用户和角色关联
        save(sysUser);
        Long userId = sysUser.getUserId();
        List<Long> roleIds = addAndUpdateUserDTO.getRoleIds();
        return addUserRole(userId, roleIds);
    }


    @Override
    public boolean updateUser(AddAndUpdateUserDTO addAndUpdateUserDTO) {
        Long userId = addAndUpdateUserDTO.getUserId();
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(addAndUpdateUserDTO,sysUser);
        //删除用户对应角色表
        deleteUserRole(userId);
        //添加用户对应角色表
        List<Long> roleIds = addAndUpdateUserDTO.getRoleIds();
        addUserRole(userId,roleIds);
        return updateById(sysUser);
    }


    @Override
    public boolean deleteUser(Long id) {
        deleteUserRole(id);
        return removeById(id);
    }

    /**
     * 校验用户名是否唯一
     */
    @Override
    public boolean checkUserNameUnique(AddAndUpdateUserDTO addAndUpdateUserDTO) {
        String staffCode = addAndUpdateUserDTO.getStaffCode();
        Long currentUserId = addAndUpdateUserDTO.getUserId();
        // 构造查询条件
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_code", staffCode);
        // 如果是更新操作并且提供了userId，则排除当前用户
        if (currentUserId != null) {
            queryWrapper.ne("user_id", currentUserId);
        }
        // 查询计数，如果为0表示没有重复用户名（或根据需求是唯一的）
        return baseMapper.selectCount(queryWrapper) == 0;
    }

    /**
     * 校验用户是否允许操作
     * @param sysUser
     */
    @Override
    public void checkUserAllowed(SysUser sysUser) {
        if (StringUtils.isNotNull(sysUser.getUserId()) && SysUser.isAdmin(sysUser.getUserId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不允许操作超级管理员用户");
        }
    }

    /**
     * 修改用户密码
     * @return
     */
    @Override
    public boolean updateUserPwd(SysUser sysUser) {
        return updateById(sysUser);
    }

    /**
     * 构建查询条件
     * @param sysUser
     * @return
     */
    private LambdaQueryWrapper<SysUser> getQueryWrapper(UserQueryDTO sysUser) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        String staffCode = sysUser.getStaffCode();
        Long deptId = sysUser.getDeptId();
        String status = sysUser.getStatus();
        if (!StrUtil.isBlank(staffCode)){
            queryWrapper.like(SysUser::getStaffCode,staffCode);
        }
        if (!ObjectUtil.isEmpty(deptId)){
            queryWrapper.eq(SysUser::getDeptId,deptId);
        }
        if (!ObjectUtil.isEmpty(status)){
            queryWrapper.eq(SysUser::getStatus,status);
        }
        return queryWrapper;
    }


    /**
     * 添加用户和角色的关联
     */
    private boolean addUserRole(Long userId,List<Long> roleIds) {
        if (roleIds.isEmpty()) {
            //添加为普通用户
            roleIds.add(6L);
        }
        // 添加用户和角色的关联
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        roleIds.forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            sysUserRoleList.add(sysUserRole);
        });
        return sysUserRoleService.saveBatch(sysUserRoleList);
    }


    /**
     * 删除用户和角色关联
     */
    private void deleteUserRole(Long userId) {
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
    }
}




