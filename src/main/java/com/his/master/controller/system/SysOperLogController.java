package com.his.master.controller.system;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.constant.BusinessType;
import com.his.master.model.dto.log.PageLogDTO;
import com.his.master.model.entity.SysOperLog;
import com.his.master.service.SysOperLogService;
import com.his.master.utils.Log;
import com.his.master.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/log")
@RestController
@Api("日志管理")
public class SysOperLogController {

    @Resource
    private SysOperLogService sysOperLogService;

    //查询所有日志
    @ApiOperation("查询所有日志")
    @PostMapping("/list")
    public BaseResponse<PageResult<SysOperLog>> listOperLog(@RequestBody PageLogDTO sysOperLog) {
        Page<SysOperLog> page = sysOperLogService.listOperLog(sysOperLog);
        return ResultUtils.success(new PageResult<>(page.getRecords(),page.getTotal()));
    }

    //删除日志
    @ApiOperation("删除日志")
    @DeleteMapping("/{id}")
    @Log(title = "日志管理", businessType = BusinessType.DELETE)
    public BaseResponse<Boolean> deleteOperLog(@PathVariable Long id) {
        boolean b = sysOperLogService.deleteOperLog(id);
        return ResultUtils.success(b);
    }

    //清空日志
    @ApiOperation("清空日志")
    @GetMapping("/clean")
    @Log(title = "日志管理", businessType = BusinessType.DELETE)
    public BaseResponse<Integer> cleanOperLog() {
        return ResultUtils.success(sysOperLogService.cleanOperLog());
    }

    //查询日志详情
    @ApiOperation("查询日志详情")
    @GetMapping("/{id}")
    public BaseResponse<SysOperLog> getOperLogById(@PathVariable Long id) {
        SysOperLog operLog = sysOperLogService.getOperLogById(id);
        return ResultUtils.success(operLog);
    }
}
