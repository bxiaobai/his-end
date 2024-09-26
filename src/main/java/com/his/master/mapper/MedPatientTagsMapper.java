package com.his.master.mapper;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.his.master.model.entity.MedPatientTag;
import com.his.master.model.entity.MedPatientTags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Administrator
* @description 针对表【med_patient_tags(患者标签表)】的数据库操作Mapper
* @createDate 2024-07-24 14:35:11
* @Entity com.his.master.model.entity.MedPatientTags
*/
public interface MedPatientTagsMapper extends BaseMapper<MedPatientTags> {

    /**
     * 排除已有标签集合
     */
    default List<MedPatientTags> getUnusedTags(List<Long> tagsId) {
        return selectList(new LambdaQueryWrapperX<MedPatientTags>()
                .notIn(MedPatientTags::getTagId, tagsId));
    }

    /**
     * 获取患者已有标签
     */
    default List<MedPatientTags> getPatientTags(Long patientId) {
        return selectList(new MPJLambdaWrapperX<MedPatientTags>()
                .selectAll(MedPatientTags.class)
                .leftJoin(MedPatientTag.class, MedPatientTag::getTagId, MedPatientTags::getTagId)
                .eq(MedPatientTag::getPatientId, patientId));
    }


}




