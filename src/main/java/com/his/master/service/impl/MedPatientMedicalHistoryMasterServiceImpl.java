package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.MedPatientMedicalHistoryMaster;
import com.his.master.service.MedPatientMedicalHistoryMasterService;
import com.his.master.mapper.MedPatientMedicalHistoryMasterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【med_patient_medical_history_master(患者既往史主表)】的数据库操作Service实现
* @createDate 2024-07-01 18:46:56
*/
@Service
public class MedPatientMedicalHistoryMasterServiceImpl extends ServiceImpl<MedPatientMedicalHistoryMasterMapper, MedPatientMedicalHistoryMaster>
    implements MedPatientMedicalHistoryMasterService{

    @Resource
    private MedPatientMedicalHistoryMasterMapper medPatientMedicalHistoryMasterMapper;
    /**
     * 根据患者id查询既往史
     * @param patientId
     * @return
     */
    @Override
    public MedPatientMedicalHistoryMaster getPatientMedicalHistoryMaster(Long patientId) {
        return medPatientMedicalHistoryMasterMapper.findByPatientId(patientId);
    }
}




