package com.his.master.controller.cure;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.dto.cure.PatientTreatmentDTO;
import com.his.master.model.dto.patient.AddOrUpdateTagDTO;
import com.his.master.model.entity.CureMedicalRecords;
import com.his.master.model.entity.MedPatientTags;
import com.his.master.service.CureMedicalRecordsService;
import com.his.master.service.MedPatientTagsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medical")
public class CureMedicalRecordsController {

    @Resource
    private CureMedicalRecordsService cureMedicalRecordsService;

    @ApiOperation(value = "根据卡号和时间获取当日详情")
    @PostMapping("/getByPatientIdAndTreatmentTime")
    public BaseResponse<Map<String, Object>> getByPatientIdAndTreatmentTime(@RequestBody PatientTreatmentDTO patientTreatmentDTO) {
        String treatmentTime = patientTreatmentDTO.getTreatmentTime();
        Long patientId = patientTreatmentDTO.getPatientId();
        if (treatmentTime == null) {
            //
            LocalDateTime now = LocalDateTime.now();
            List<CureMedicalRecords> allTreatmentTime = cureMedicalRecordsService.getAllTreatmentTime(patientId);
            // 使用流API按data字段降序排序
            List<CureMedicalRecords> sortedItems = allTreatmentTime.stream()
                    .sorted(Comparator.comparing(CureMedicalRecords::getTreatmentTime).reversed())
                    .collect(Collectors.toList());
            // 获取排序后的第一个元素
            CureMedicalRecords firstItem = sortedItems.isEmpty() ? null : sortedItems.get(0);
            if (firstItem != null) {
                patientTreatmentDTO.setTreatmentTime(DateUtil.format(firstItem.getTreatmentTime(), "yyyy-MM-dd"));
            }
        }
        Map<String, Object> byPatientIdAndTreatmentTime = cureMedicalRecordsService.getByPatientIdAndTreatmentTime(patientTreatmentDTO);
        return ResultUtils.success(byPatientIdAndTreatmentTime);
    }

    @ApiOperation(value = "根据id查询患者所有的治疗时间")
    @GetMapping("/getByPatientIdAndTreatmentTime/{patientId}")
    public BaseResponse<List<String>> getByPatientIdAndTreatmentTime(@PathVariable("patientId") Long patientId) {
        List<CureMedicalRecords> allTreatmentTime = cureMedicalRecordsService.getAllTreatmentTime(patientId);
        List<String> a = allTreatmentTime.stream()
                .sorted(Comparator.comparing(CureMedicalRecords::getTreatmentTime).reversed())
                .map(e -> DateUtil.format(e.getTreatmentTime(), "yyyy-MM-dd"))
                .collect(Collectors.toList());
        return ResultUtils.success(a);
    }

    /**
     * 添加治疗记录
     */
    @ApiOperation(value = "添加治疗记录")
    @PostMapping("/add")
    public BaseResponse<Boolean> add(@RequestBody CureMedicalRecords cureMedicalRecords) {
        return ResultUtils.success(cureMedicalRecordsService.addTreatment(cureMedicalRecords));
    }

    /**
     * 删除治疗记录
     */
    @ApiOperation(value = "删除治疗记录")
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> delete(@PathVariable("id") Long id) {
        return ResultUtils.success(cureMedicalRecordsService.deleteTreatment(id));
    }


}
