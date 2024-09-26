package com.his.master.mapper;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.his.master.model.entity.MedPatientTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;

import java.util.Collection;
import java.util.List;

/**
* @author Administrator
* @description 针对表【med_patient_tag(患者标签映射表)】的数据库操作Mapper
* @createDate 2024-07-24 14:35:11
* @Entity com.his.master.model.entity.MedPatientTag
*/
public interface MedPatientTagMapper extends BaseMapperX<MedPatientTag> {

    /**
     * 删除患者关联的标签
     */
    default int deleteTagsAndPatient(Long patientId, Long tagsId) {
        return delete(new LambdaQueryWrapperX<MedPatientTag>()
                .eq(MedPatientTag::getPatientId, patientId)
                .eq(MedPatientTag::getTagId, tagsId));
    }

    /**
     * 添加患者关联的标签
     */
    default int insertTags(Long patientId, Long tagsId) {

        MedPatientTag tags = new MedPatientTag();
        tags.setPatientId(patientId);
        tags.setTagId(tagsId);
        return insert(tags);
    }

    /**
     * 获取患者已有标签id
     */
    default List<MedPatientTag> getPatientTags(Long patientId) {
        return selectList(new LambdaQueryWrapperX<MedPatientTag>()
                .eq(MedPatientTag::getPatientId, patientId));
    }

    /**
     * 判断患者是否已有该标签
     */
    default boolean isExistPatientTag(Long patientId, Long tagsId) {
        return selectCount(new LambdaQueryWrapperX<MedPatientTag>()
                .eq(MedPatientTag::getPatientId, patientId)
                .eq(MedPatientTag::getTagId, tagsId)) == 0;
    }
}




