package com.his.master.mapper;

import com.his.master.model.entity.CureAdverseReactions;
import com.his.master.model.entity.CureMedicalRecords;
import com.his.master.model.entity.CurePostTreatmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;

/**
 * @author Administrator
 * @description 针对表【cure_post_treatment_info】的数据库操作Mapper
 * @createDate 2024-09-06 15:26:33
 * @Entity com.his.master.model.entity.CurePostTreatmentInfo
 */
public interface CurePostTreatmentInfoMapper extends BaseMapperX<CurePostTreatmentInfo> {

    default CurePostTreatmentInfo getCurePostTreatmentInfoById(Long id) {
        MPJLambdaWrapperX<CurePostTreatmentInfo> mpjLambdaWrapperX = new MPJLambdaWrapperX<>();
        mpjLambdaWrapperX
                .selectAll(CurePostTreatmentInfo.class)
                .selectCollection(CureAdverseReactions.class,CurePostTreatmentInfo::getCureAdverseReactions)
                .leftJoin(CureAdverseReactions.class, CureAdverseReactions::getPostTreatmentInfoId, CurePostTreatmentInfo::getId)
                .eq(CurePostTreatmentInfo::getMedicalRecordId, id);
        return selectJoinOne(CurePostTreatmentInfo.class, mpjLambdaWrapperX);
    }
}




