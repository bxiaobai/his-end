package com.his.master.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.constant.BusinessType;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.user.AddAndUpdateUserDTO;
import com.his.master.model.dto.user.UserQueryDTO;
import com.his.master.model.entity.SysUser;
import com.his.master.service.SysUserRoleService;
import com.his.master.service.SysUserService;
import com.his.master.utils.Log;
import com.his.master.utils.redis.RedisUtils;
import com.his.master.utils.security.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("/users")
@RestController
@Api("角色管理")
@Slf4j
public class SysUserController {

    @Resource
    SysUserService sysUserService;


    @ApiOperation("分页查询角色列表")
    @PostMapping("/list")
    public BaseResponse<Page<SysUser>> listUser(@RequestBody UserQueryDTO userQueryDTO) {
        return ResultUtils.success(sysUserService.listUser(userQueryDTO));
    }

    /**
     * 查询用户详情
     */
    @ApiOperation("查询用户详情")
    @GetMapping("/{id}")
    public BaseResponse<SysUser> getUserById(@PathVariable Long id) {
        //获取用户对应角色
        return ResultUtils.success( sysUserService.getUserById(id));
    }

    /**
     * 添加用户
     */
    @ApiOperation("添加用户")
    @PostMapping("/add")
    public BaseResponse<Boolean> addUser(@Valid @RequestBody AddAndUpdateUserDTO addAndUpdateUserDTO) {
        boolean checkedUserNameUnique = sysUserService.checkUserNameUnique(addAndUpdateUserDTO);
        if (!checkedUserNameUnique) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已经存在");
        }
        return ResultUtils.success(sysUserService.addUser(addAndUpdateUserDTO));

    }

    /**
     * 修改用户
     *
     * @return
     */
    @ApiOperation("修改用户")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@Valid @RequestBody AddAndUpdateUserDTO addAndUpdateUserDTO) {
        boolean checkedUserNameUnique = sysUserService.checkUserNameUnique(addAndUpdateUserDTO);
        if (!checkedUserNameUnique) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已经存在");
        }
        sysUserService.checkUserAllowed(new SysUser(addAndUpdateUserDTO.getUserId()));
        return ResultUtils.success(sysUserService.updateUser(addAndUpdateUserDTO));

    }


    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteUser(@PathVariable Long id) {
        sysUserService.checkUserAllowed(new SysUser(id));
        //不允许删除自己
        if (SecurityUtils.getUserId().equals(id)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不允许删除自己");
        }
        return ResultUtils.success(sysUserService.deleteUser(id));
    }


    /**
     * 重置密码
     */
    @ApiOperation("重置密码")
    @PostMapping("/resetPwd")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    public BaseResponse<Boolean> updateUserPwd(@RequestBody SysUser sysUser) {
        sysUserService.checkUserAllowed(sysUser);
        sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
        return ResultUtils.success(sysUserService.updateUserPwd(sysUser));
    }

}
