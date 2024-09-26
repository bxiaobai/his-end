package com.his.master.service;

import com.his.master.model.entity.MedPatientMedicalHistoryMaster;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【med_patient_medical_history_master(患者既往史主表)】的数据库操作Service
* @createDate 2024-07-01 18:46:56
*/
public interface MedPatientMedicalHistoryMasterService extends IService<MedPatientMedicalHistoryMaster> {

    /**
     * 根据患者id查询既往史
     */
    MedPatientMedicalHistoryMaster getPatientMedicalHistoryMaster(Long patientId);
}
