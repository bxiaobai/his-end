package com.his.master.controller.system;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.menu.MenuAddDTO;
import com.his.master.model.dto.menu.MenuQueryDTO;
import com.his.master.model.entity.SysMenu;
import com.his.master.model.entity.SysUser;
import com.his.master.model.vo.menus.MenuTreeVO;
import com.his.master.service.SysMenuService;
import com.his.master.utils.security.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/menu")
@RestController
@Api("菜单服务")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;


    @PostMapping("/list")
    @ApiOperation("查询所有惨淡")
    public BaseResponse<List<SysMenu>> listMenu(@RequestBody MenuQueryDTO menuQueryDTO) {
        List<SysMenu> allMenu = sysMenuService.getAllMenu(menuQueryDTO);
        return ResultUtils.success(sysMenuService.buildMenuTree(allMenu));
    }

    //获取菜单树形结构
    @PostMapping("/list/tree")
    @ApiOperation("获取菜单树形结构")
    public BaseResponse<List<SysMenu>> getMenuTree(@RequestBody MenuQueryDTO menuQueryDTO) {
        List<SysMenu> menus = sysMenuService.getAllMenu(menuQueryDTO);
        return ResultUtils.success(sysMenuService.buildMenuTree(menus));
    }
    //添加菜单
    @PostMapping("/add")
    @ApiOperation("添加菜单")
    public BaseResponse<Boolean> addMenu(@RequestBody MenuAddDTO menu) {
        if (!sysMenuService.checkMenuNameUnique(menu)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "菜单名称已存在");
        }
        return ResultUtils.success(sysMenuService.addMenu(menu));
    }

    //删除菜单
    @ApiOperation("删除菜单")
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteMenu(@PathVariable("id") Long menuId) {
        if (sysMenuService.hasChildByMenuId(menuId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "存在子菜单,不允许删除");
        }
        return ResultUtils.success(sysMenuService.deleteMenu(menuId));
    }

    //修改菜单
    @ApiOperation("修改菜单")
    @PutMapping("/update")
    public BaseResponse<Boolean> updateMenu(@RequestBody MenuAddDTO menu) {
        if (!sysMenuService.checkMenuNameUnique(menu)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "菜单名称已存在");
        }
        return ResultUtils.success(sysMenuService.updateMenuById(menu));
    }

    //根据菜单id获取详情
    @ApiOperation("根据菜单id获取详情")
    @GetMapping("/{id}")
    public BaseResponse<SysMenu> getMenuById(@PathVariable Long id) {
        return ResultUtils.success(sysMenuService.selectMenuById(id));
    }


}
