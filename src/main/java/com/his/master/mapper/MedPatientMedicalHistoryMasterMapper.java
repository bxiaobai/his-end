package com.his.master.mapper;

import com.his.master.model.entity.*;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;

/**
* @author Administrator
* @description 针对表【med_patient_medical_history_master(患者既往史主表)】的数据库操作Mapper
* @createDate 2024-07-01 18:46:56
* @Entity com.his.master.model.entity.MedPatientMedicalHistoryMaster
*/
public interface MedPatientMedicalHistoryMasterMapper extends BaseMapperX<MedPatientMedicalHistoryMaster> {

    /**
     * 根据患者id查询既往史
     */
   default MedPatientMedicalHistoryMaster findByPatientId(Long patientId){
       MPJLambdaWrapperX<MedPatientMedicalHistoryMaster> mpjLambdaWrapper =   new MPJLambdaWrapperX<>();
       mpjLambdaWrapper
               .selectAll(MedPatientMedicalHistoryMaster.class)
               .selectCollection(MedSurgeryHistoryDetails.class, MedPatientMedicalHistoryMaster::getSurgeryHistoryDetailsList)
               .selectCollection(MedAllergyHistoryDetails.class, MedPatientMedicalHistoryMaster::getAllergicHistory)
               .selectCollection(MedDiseaseHistoryDetails.class, MedPatientMedicalHistoryMaster::getDiseaseHistory)
               .leftJoin(MedAllergyHistoryDetails.class, MedAllergyHistoryDetails::getHistoryId, MedPatientMedicalHistoryMaster::getHistoryId)
               .leftJoin(MedDiseaseHistoryDetails.class, MedDiseaseHistoryDetails::getHistoryId, MedPatientMedicalHistoryMaster::getHistoryId)
               .leftJoin(MedSurgeryHistoryDetails.class, MedSurgeryHistoryDetails::getHistoryId, MedPatientMedicalHistoryMaster::getHistoryId)
               .eq(MedPatientMedicalHistoryMaster::getPatientId,patientId);
       return  selectJoinOne(MedPatientMedicalHistoryMaster.class, mpjLambdaWrapper);
   }
}




