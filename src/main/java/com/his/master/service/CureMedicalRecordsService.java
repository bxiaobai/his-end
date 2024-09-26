package com.his.master.service;

import com.his.master.model.dto.cure.PatientTreatmentDTO;
import com.his.master.model.entity.CureMedicalRecords;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【cure_medical_records】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureMedicalRecordsService extends IService<CureMedicalRecords> {

    /**
     * 根据患者id和治疗时间查询治疗记录
     */
    Map<String ,Object> getByPatientIdAndTreatmentTime(PatientTreatmentDTO patientId);

    /**
     * 查询该患者所有治疗记录
     */
    List<CureMedicalRecords> getAllTreatmentTime(Long patientId);

    /**
     * 添加治疗记录
     */
    boolean addTreatment(CureMedicalRecords cureMedicalRecords);


    /**
     * 删除治疗记录
     */
    boolean deleteTreatment(Long id);


    /**
     * 根据方案id查询医疗记录
     */
    List<CureMedicalRecords> getMedicalRecordsByPlanId(Long planId);
}
