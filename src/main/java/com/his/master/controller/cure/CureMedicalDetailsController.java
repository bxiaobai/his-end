package com.his.master.controller.cure;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.entity.CureMedicalDetails;
import com.his.master.service.CureMedicalDetailsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;

@Api("添加医疗记录详情接口")
@RestController
@RequestMapping("/medical/details")
public class CureMedicalDetailsController {

    @Resource
    CureMedicalDetailsService cureMedicalDetailsService;


    @PostMapping("/add")
    public BaseResponse<Boolean> addDetails(@RequestBody CureMedicalDetails cureMedicalDetails){
        boolean exist = cureMedicalDetailsService.isExist(cureMedicalDetails.getMedicalRecordId());
        if (!exist){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能重复添加！");
        }
        return ResultUtils.success(cureMedicalDetailsService.addTreatment(cureMedicalDetails));
    }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateDetails(@RequestBody CureMedicalDetails cureMedicalDetails){
        return ResultUtils.success(cureMedicalDetailsService.updateTreatment(cureMedicalDetails));
    }



    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteDetails(@PathVariable Long id){
        return ResultUtils.success(cureMedicalDetailsService.deleteTreatment(id));
    }
}
