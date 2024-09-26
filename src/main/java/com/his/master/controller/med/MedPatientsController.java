package com.his.master.controller.med;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.master.common.BaseResponse;
import com.his.master.common.ErrorCode;
import com.his.master.common.ResultUtils;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.med.MedPatientsQueryDTO;
import com.his.master.model.dto.patient.MedPatientsAddDTO;
import com.his.master.model.entity.MedPatients;
import com.his.master.model.vo.patient.MedPatientsVO;
import com.his.master.service.CureMedicalRecordsService;
import com.his.master.service.MedPatientsService;
import com.his.master.utils.PageResult;
import com.his.master.utils.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patient")
public class MedPatientsController {

    @Resource
    private MedPatientsService medPatientService;

    @Resource
    CureMedicalRecordsService cureMedicalRecordsService;

    /**
     * 患者列表
     */
    @ApiOperation(value = "分页查询患者列表")
    @PostMapping("/listPatients")
    public BaseResponse<PageResult<MedPatientsVO>> listPatients(@RequestBody MedPatientsQueryDTO medPatientsQueryDTO) {
      return ResultUtils.success( medPatientService.listPatients(medPatientsQueryDTO));
    }

    /**
     * 新增患者
     * @param medPatients
     * @return
     */
    @ApiOperation(value = "新增患者")
    @PostMapping("/addPatients")
    public BaseResponse<Boolean> addPatients(@RequestBody MedPatientsAddDTO medPatients) {
        return ResultUtils.success(medPatientService.addPatients(medPatients));
    }


    @ApiOperation(value = "删除患者")
    @GetMapping("/deletePatients/{id}")
    public BaseResponse<Boolean> deletePatients(@PathVariable("id") Long id) {
        //判断是不是管理员
        boolean admin = SecurityUtils.isAdmin(SecurityUtils.getUserId());
        if (!admin){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "非管理员不能操作");
        }
        return ResultUtils.success(medPatientService.deletePatients(id));
    }


    @ApiOperation(value = "更新患者")
    @PostMapping("/updatePatients")
    public BaseResponse<Boolean> updatePatients(@RequestBody MedPatients medPatients) {
        return ResultUtils.success(medPatientService.updatePatients(medPatients));
    }


    @ApiOperation(value = "查询患者详情")
    @GetMapping("/getPatientsById/{id}")
    public BaseResponse<Map<String, Object>> getPatientsById(@PathVariable("id") Long id) {
        return ResultUtils.success(medPatientService.getPatientsById(id));
    }




}
