package com.his.master.mapper;

import com.his.master.model.dto.med.MedPatientsQueryDTO;
import com.his.master.model.entity.*;
import com.his.master.model.vo.patient.MedPatientsVO;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;
import com.his.master.utils.PageResult;

/**
 * @author Administrator
 * @description 针对表【med_patients(患者基本信息表)】的数据库操作Mapper
 * @createDate 2024-07-01 18:46:56
 * @Entity com.his.master.model.entity.MedPatients
 */
public interface MedPatientsMapper extends BaseMapperX<MedPatients> {

    /**
     * 获取患者列表
     * @param medPatients
     * @return
     */
    default PageResult<MedPatients> listPatients(MedPatientsQueryDTO medPatients) {
        MPJLambdaWrapperX<MedPatients> mpjLambdaWrapper =   new MPJLambdaWrapperX<>();
        mpjLambdaWrapper.selectAll(MedPatients.class)
                .selectFunc(() -> "GROUP_CONCAT(DISTINCT t2.tag_name SEPARATOR ', ')", MedPatientTags::getTagName)
                .leftJoin(MedPatientTag.class, MedPatientTag::getPatientId, MedPatients::getPatientId)
                .leftJoin(MedPatientTags.class, MedPatientTags::getTagId, MedPatientTag::getTagId)
                .leftJoin(MedPatientGroup.class, MedPatientGroup::getPatientId, MedPatients::getPatientId)
                .leftJoin(MedGroups.class, MedGroups::getGroupId, MedPatientGroup::getGroupId)
                .likeIfExists(MedPatientTags::getTagName, medPatients.getTagName())
                .likeIfExists(MedPatients::getPatientName, medPatients.getPatientName())
                .likeIfExists(MedPatients::getMedicalNumber, medPatients.getMedicalNumber())
                .likeIfExists(MedGroups::getGroupName, medPatients.getGroup())
                .groupBy(MedPatients::getPatientId);
        return selectJoinPage(medPatients,MedPatients.class ,mpjLambdaWrapper);
    }



}




