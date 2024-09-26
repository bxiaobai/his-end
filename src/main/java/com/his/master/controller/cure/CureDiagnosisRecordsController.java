package com.his.master.controller.cure;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.entity.CureDiagnosisRecords;
import com.his.master.model.entity.CureMedicalDetails;
import com.his.master.service.CureDiagnosisRecordsService;
import com.his.master.service.CureMedicalDetailsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("添加诊断结果")
@RestController
@RequestMapping("/medical/diagnosis")
public class CureDiagnosisRecordsController {

    @Resource
    CureDiagnosisRecordsService cureDiagnosisRecordsService;


    @PostMapping("/add")
    public BaseResponse<Boolean> addDiagnosis(@RequestBody CureDiagnosisRecords cureMedicalDetails){
        boolean exist = cureDiagnosisRecordsService.isExist(cureMedicalDetails.getMedicalRecordId());
        if (!exist){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能重复添加！");
        }
        return ResultUtils.success(cureDiagnosisRecordsService.addDiagnosis(cureMedicalDetails));
    }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateDiagnosis(@RequestBody CureDiagnosisRecords cureMedicalDetails){
        return ResultUtils.success(cureDiagnosisRecordsService.updateDiagnosis(cureMedicalDetails));
    }



    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteDiagnosis(@PathVariable Long id){
        return ResultUtils.success(cureDiagnosisRecordsService.deleteDiagnosis(id));
    }
}
