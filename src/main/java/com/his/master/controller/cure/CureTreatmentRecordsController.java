package com.his.master.controller.cure;

import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.cure.CureTreatmentRecordsDTO;
import com.his.master.model.entity.CureChemotherapyRecords;
import com.his.master.model.entity.CureTreatmentRecords;
import com.his.master.service.CureChemotherapyRecordsService;
import com.his.master.service.CureTreatmentRecordsService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("医疗记录")
@RestController
@RequestMapping("/medical/treatment")
public class CureTreatmentRecordsController {

    @Resource
    CureTreatmentRecordsService cureTreatmentRecordsService;


    @PostMapping("/list")
    public BaseResponse<PageResult<CureTreatmentRecords>> listTreatment(@RequestBody CureTreatmentRecordsDTO cureMedicalDetails){
        return ResultUtils.success(cureTreatmentRecordsService.getHistoryReport(cureMedicalDetails));
    }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateTreatment(@RequestBody CureTreatmentRecords cureMedicalDetails){
        return ResultUtils.success(cureTreatmentRecordsService.updateHistoryReport(cureMedicalDetails));
    }



    @GetMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteTreatment(@PathVariable Long id){
        return ResultUtils.success(cureTreatmentRecordsService.deleteHistoryReport(id));
    }

    @GetMapping("/get/{id}")
    public BaseResponse<CureTreatmentRecords> getTreatmentInfo(@PathVariable Long id){
        return ResultUtils.success(cureTreatmentRecordsService.getHistoryReportDetails(id));
    }
}
