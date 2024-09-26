package com.his.master.controller.cure;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.entity.CureChemotherapyRecords;
import com.his.master.model.entity.CureVitalsignsRecords;
import com.his.master.service.CureChemotherapyRecordsService;
import com.his.master.service.CureVitalsignsRecordsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("添加体征详情接口")
@RestController
@RequestMapping("/medical/chemotherapy")
public class CureChemotherapyRecordsController {

    @Resource
    CureChemotherapyRecordsService cureChemotherapyRecordsService;


    @PostMapping("/add")
    public BaseResponse<Boolean> addChemotherapy(@RequestBody CureChemotherapyRecords cureMedicalDetails){
        boolean exist = cureChemotherapyRecordsService.isExist(cureMedicalDetails.getMedicalRecordId());
        if (!exist){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"不能重复添加！");
        }
        return ResultUtils.success(cureChemotherapyRecordsService.addChemotherapy(cureMedicalDetails));
    }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateChemotherapy(@RequestBody CureChemotherapyRecords cureMedicalDetails){
        return ResultUtils.success(cureChemotherapyRecordsService.updateChemotherapy(cureMedicalDetails));
    }



    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteChemotherapy(@PathVariable Long id){
        return ResultUtils.success(cureChemotherapyRecordsService.deleteChemotherapy(id));
    }
}
