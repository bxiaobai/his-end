package com.his.master.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.entity.SysDept;
import com.his.master.model.vo.dept.DeptTreeVO;
import com.his.master.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/dept")
@RestController
@Api("科室服务")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    //查询全部科室树形
    @GetMapping("/list")
    @ApiOperation("科室列表")
    public BaseResponse<List<DeptTreeVO>> listTreeDept() {
        return ResultUtils.success(sysDeptService.listTreeDept());
    }

    //查询全部科室树形
    @GetMapping("/lists")
    @ApiOperation("科室列表")
    public BaseResponse<List<SysDept>> listDept() {
        return ResultUtils.success(sysDeptService.listDept());
    }

    @PostMapping("/post")
    @ApiOperation("科室列表")
    public BaseResponse<List<DeptTreeVO>> listTreeDept(@RequestBody SysDept sysDept) {
        return ResultUtils.success(sysDeptService.postTreeDept(sysDept));
    }

    @GetMapping("/get/{id}")
    @ApiOperation("查询详情")
    public BaseResponse<SysDept> getDeptById(@PathVariable("id") Long id) {
        return ResultUtils.success(sysDeptService.getById(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增科室")
    public BaseResponse<Boolean> addDept(@RequestBody SysDept sysDept) {
        return ResultUtils.success(sysDeptService.save(sysDept));
    }

    @PostMapping("/update")
    @ApiOperation("修改科室")
    public BaseResponse<Boolean> updateDept(@RequestBody SysDept sysDept) {
        return ResultUtils.success(sysDeptService.updateById(sysDept));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除科室")
    public BaseResponse<Boolean> deleteDept(@PathVariable("id") Long id) {
        return ResultUtils.success(sysDeptService.removeById(id));
    }
}
