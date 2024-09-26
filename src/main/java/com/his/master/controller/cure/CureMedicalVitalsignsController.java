package com.his.master.controller.cure;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.entity.CureMedicalDetails;
import com.his.master.model.entity.CureVitalsignsRecords;
import com.his.master.service.CureMedicalDetailsService;
import com.his.master.service.CureVitalsignsRecordsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;

@Api("添加体征详情接口")
@RestController
@RequestMapping("/medical/vitals")
public class CureMedicalVitalsignsController {

    @Resource
    CureVitalsignsRecordsService cureVitalsignsRecordsService;


    @PostMapping("/add")
    public BaseResponse<Boolean> addDetails(@RequestBody CureVitalsignsRecords cureMedicalDetails){
        boolean exist = cureVitalsignsRecordsService.isExist(cureMedicalDetails.getMedicalRecordId());
        if (!exist){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能重复添加！");
        }
        return ResultUtils.success(cureVitalsignsRecordsService.addTreatment(cureMedicalDetails));
    }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateDetails(@RequestBody CureVitalsignsRecords cureMedicalDetails){
        return ResultUtils.success(cureVitalsignsRecordsService.updateTreatment(cureMedicalDetails));
    }



    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteDetails(@PathVariable Long id){
        return ResultUtils.success(cureVitalsignsRecordsService.deleteTreatment(id));
    }
}
