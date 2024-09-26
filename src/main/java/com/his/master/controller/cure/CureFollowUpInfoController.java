package com.his.master.controller.cure;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.cure.CureFollowUpInfoDTO;
import com.his.master.model.entity.CureChemotherapyRecords;
import com.his.master.model.entity.CureFollowUpInfo;
import com.his.master.service.CureChemotherapyRecordsService;
import com.his.master.service.CureFollowUpInfoService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("添加体征详情接口")
@RestController
@RequestMapping("/medical/cureFollow")
public class CureFollowUpInfoController {

    @Resource
    CureFollowUpInfoService cureFollowUpInfoService;


    @PostMapping("/add")
    public BaseResponse<Boolean> addFollowUp(@RequestBody CureFollowUpInfo cureMedicalDetails){
        boolean exist = cureFollowUpInfoService.isExist(cureMedicalDetails.getMedicalRecordId());
        if (!exist){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能重复添加！");
        }
        return ResultUtils.success(cureFollowUpInfoService.addFollowUpInfo(cureMedicalDetails));
    }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateFollowUp(@RequestBody CureFollowUpInfo cureMedicalDetails){
        return ResultUtils.success(cureFollowUpInfoService.updateFollowUpInfo(cureMedicalDetails));
    }



    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteFollowUp(@PathVariable Long id){
        return ResultUtils.success(cureFollowUpInfoService.deleteFollowUpInfo(id));
    }


    /**
     * 根据患者id查询患者所有随访记录
     */
    @PostMapping("/page")
    public BaseResponse<PageResult<CureFollowUpInfo>> getByPatientId(@RequestBody CureFollowUpInfoDTO cureFollowUpInfo){
        PageResult<CureFollowUpInfo> followUpInfo = cureFollowUpInfoService.getFollowUpInfo(cureFollowUpInfo);
        return ResultUtils.success(followUpInfo);
    }

    /**
     * 根据id查询随访详情
     */
    @GetMapping("/{id}")
    public BaseResponse<CureFollowUpInfo> getFollowUpById(@PathVariable Long id){
        return ResultUtils.success(cureFollowUpInfoService.getCureFollowUpInfoById(id));
    }

    /**
     * 确认随访状态
     */
    @PostMapping("/confirm")
    public BaseResponse<Boolean> confirmFollowUp(@RequestBody CureFollowUpInfo cureFollowUpInfo){
        return ResultUtils.success(cureFollowUpInfoService.updateFollowUpInfo(cureFollowUpInfo));
    }
}
