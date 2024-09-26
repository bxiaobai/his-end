package com.his.master.controller.med;

import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.dto.patient.AddOrUpdateTagDTO;
import com.his.master.model.entity.MedPatientTags;
import com.his.master.service.MedPatientTagsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class MedTagsController {

    @Resource
    private MedPatientTagsService medTagsService;

    @PostMapping("/add")
    public BaseResponse<Boolean> addOrUpdatePatientTags(@RequestBody AddOrUpdateTagDTO medPatientTags) {
        medTagsService.addOrUpdatePatientTags(medPatientTags);
        return ResultUtils.success(true);
    }

    /**
     * 获取未使用的标签
     * @param id
     * @return
     */
    @GetMapping("/listNotTags/{id}")
    public BaseResponse<List<MedPatientTags>> listPatientTags(@PathVariable("id") Long id) {
        return ResultUtils.success(medTagsService.getUnusedTags(id));
    }

    /**
     * 删除患者标签
     * @param id
     * @param patientId
     * @return
     */
    @GetMapping("/delete/{id}/{patientId}")
    public BaseResponse<Boolean> deletePatientTags(
            @PathVariable("id") Long id,
            @PathVariable("patientId") Long patientId) {
        medTagsService.deletePatientTags(id, patientId);
        return ResultUtils.success(true);
    }

    /**
     * 获取使用的标签
     * @param id
     * @return
     */
    @GetMapping("/listTags/{id}")
    public BaseResponse<List<MedPatientTags>> listTags(@PathVariable("id") Long id) {
        return ResultUtils.success(medTagsService.listPatientTags(id));
    }


    /**
     * 获取所有标签
     */
    @GetMapping("/listAllTags")
    public BaseResponse<List<MedPatientTags>> listAllTags() {
        return ResultUtils.success(medTagsService.list());
    }
}
