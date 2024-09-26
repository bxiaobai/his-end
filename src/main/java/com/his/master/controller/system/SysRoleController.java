package com.his.master.controller.system;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.role.RoleAddDTO;
import com.his.master.model.dto.role.RoleQueryDTO;
import com.his.master.model.entity.SysRole;
import com.his.master.service.SysRoleService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@Api("角色管理")
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;


    /**
     * 分页查询角色列表
     */
    @ApiOperation("分页查询角色列表")
    @PostMapping("/list")
    public BaseResponse<PageResult<SysRole>> listRole(@RequestBody RoleQueryDTO roleQueryDTO) {
        return ResultUtils.success(sysRoleService.listRole(roleQueryDTO));
    }


    /**
     * 角色详情
     */
    @ApiOperation("角色详情")
    @GetMapping("/{id}")
    public BaseResponse<SysRole> getRoleById(@PathVariable Long id) {
        return ResultUtils.success(sysRoleService.getRoleById(id));
    }

    /**
     * 添加角色
     */
    @ApiOperation("添加角色")
    @PostMapping("/add")
    public BaseResponse<Boolean> addRole( @RequestBody RoleAddDTO roleAddDTO) {
        if (!sysRoleService.checkRoleNameUnique(roleAddDTO)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"角色名称重复");
        }
        if (!sysRoleService.checkRoleKeyUnique(roleAddDTO)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"权限标识重复");
        }
        return ResultUtils.success(sysRoleService.addRole(roleAddDTO));
    }

    /**
     * 修改角色
     */
    @ApiOperation("修改角色")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateRole(@Valid @RequestBody RoleAddDTO roleAddDTO) {
        if (!sysRoleService.checkRoleNameUnique(roleAddDTO)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"角色名称重复");
        }
        if (!sysRoleService.checkRoleKeyUnique(roleAddDTO)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"权限标识重复");
        }
        return ResultUtils.success(sysRoleService.updateRole(roleAddDTO));
    }

    /**
     * 删除角色
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteRole(@PathVariable Long id) {
        return ResultUtils.success(sysRoleService.deleteRole(id));
    }

}
