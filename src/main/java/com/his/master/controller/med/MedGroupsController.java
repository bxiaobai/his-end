package com.his.master.controller.med;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.constant.BusinessType;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.patient.AddGroupDTO;
import com.his.master.model.entity.MedGroups;
import com.his.master.service.MedGroupsService;
import com.his.master.utils.Log;
import com.his.master.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/group")
public class MedGroupsController {

    @Resource
    private MedGroupsService medGroupsService;

    /**
     * 分页查询分组信息
     * @param medGroups
     * @return
     */
    @ApiOperation("分页查询分组信息")
    @PostMapping("/list")
    public BaseResponse<PageResult<MedGroups>> listMedGroups(@RequestBody MedGroups medGroups) {
        Page<MedGroups> medGroupsPage = medGroupsService.listGroups(medGroups);
        PageResult<MedGroups> medGroupsPageResult = new PageResult<>(medGroupsPage.getRecords(), medGroupsPage.getTotal());
        return ResultUtils.success(medGroupsPageResult);
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @ApiOperation("删除分组")
    @DeleteMapping("/{id}")
    @Log(title = "分组管理", businessType = BusinessType.DELETE)
    public BaseResponse<Boolean> deleteGroups(@PathVariable Long id) {
        MedGroups medGroups = new MedGroups();
        medGroups.setGroupId(id);
        boolean existGroupsPatients = medGroupsService.isExistGroupsPatients(medGroups);
        if (!existGroupsPatients) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该分组下存在患者，不能删除");
        }
        return ResultUtils.success(medGroupsService.deleteGroups(id));
    }


    /**
     * 添加分组
     * @param medGroups
     * @return
     */
    @ApiOperation("添加分组")
    @PostMapping("/add")
    @Log(title = "分组管理", businessType = BusinessType.INSERT)
    public BaseResponse<Boolean> addGroups(@RequestBody MedGroups medGroups) {
        if (medGroupsService.isExistGroupsName(medGroups)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分组名称已存在");
        }
        return ResultUtils.success(medGroupsService.addGroups(medGroups));
    }

    /**
     * 修改分组
     * @param medGroups
     * @return
     */
    @ApiOperation("修改分组")
    @PostMapping("/update")
    @Log(title = "分组管理", businessType = BusinessType.UPDATE)
    public BaseResponse<Boolean> updateGroups(@RequestBody MedGroups medGroups) {
        if (medGroupsService.isExistGroupsName(medGroups)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "分组名称已存在");
        }
        return ResultUtils.success(medGroupsService.updateGroups(medGroups));
    }

    @ApiOperation("根据id查询分组")
    @GetMapping("/{id}")
    public BaseResponse<MedGroups> getGroupsById(@PathVariable Long id) {
        return ResultUtils.success(medGroupsService.getGroupsById(id));
    }


    @ApiOperation("查询分组列表")
    @GetMapping("/list")
    public BaseResponse<List<MedGroups>> listGroups() {
        return ResultUtils.success(medGroupsService.listAllGroups());
    }


    /**
     * 获取患者所属分组
     */
    @ApiOperation("获取患者所属分组")
    @GetMapping("/patientGroups/{patientId}")
    public BaseResponse<List<Long>> getPatientGroups(@PathVariable Long patientId) {
        return ResultUtils.success(medGroupsService.getPatientGroups(patientId));
    }

    /**
     * 添加患者分组
     */
    @ApiOperation("添加患者所属分组")
    @PostMapping("/addPatientGroups")
    @Log(title = "患者分组管理", businessType = BusinessType.INSERT)
    public BaseResponse<Boolean> addPatientGroups(@RequestBody AddGroupDTO addGroupDTO) {
        medGroupsService.addPatientGroups(addGroupDTO.getPatientId(), addGroupDTO.getGroupsId());
        return ResultUtils.success(true);
    }


}
