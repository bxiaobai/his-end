package com.his.master.controller.cure;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.entity.CureChemotherapyRecords;
import com.his.master.model.entity.CurePostTreatmentInfo;
import com.his.master.service.CureChemotherapyRecordsService;
import com.his.master.service.CurePostTreatmentInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("添加体征详情接口")
@RestController
@RequestMapping("/medical/postTreatment")
public class CurePostTreatmentInfoRecordsController {

    @Resource
    CurePostTreatmentInfoService curePostTreatmentInfoService;


    @PostMapping("/add")
    public BaseResponse<Boolean> addCurePost(@RequestBody CurePostTreatmentInfo cureMedicalDetails){
        boolean exist = curePostTreatmentInfoService.isExist(cureMedicalDetails.getMedicalRecordId());
        if (!exist){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能重复添加！");
        }
        return ResultUtils.success(curePostTreatmentInfoService.addPostTreatment(cureMedicalDetails));
    }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateCurePost(@RequestBody CurePostTreatmentInfo cureMedicalDetails){
        return ResultUtils.success(curePostTreatmentInfoService.updatePostTreatment(cureMedicalDetails));
    }



    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteCurePost(@PathVariable Long id){
        return ResultUtils.success(curePostTreatmentInfoService.deletePostTreatment(id));
    }
}
