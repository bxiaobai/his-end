package com.his.master.mapper;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.his.master.model.dto.cure.CureFollowUpInfoDTO;
import com.his.master.model.entity.CureAdverseReactions;
import com.his.master.model.entity.CureFollowUpInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.model.entity.CureFollowUpInfoDetails;
import com.his.master.model.entity.CurePostTreatmentInfo;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;
import com.his.master.utils.PageResult;

import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【cure_follow_up_info】的数据库操作Mapper
 * @createDate 2024-09-06 15:26:33
 * @Entity com.his.master.model.entity.CureFollowUpInfo
 */
public interface CureFollowUpInfoMapper extends BaseMapperX<CureFollowUpInfo> {
    default CureFollowUpInfo getCureFollowUpInfoById(Long id) {
        MPJLambdaWrapperX<CureFollowUpInfo> mpjLambdaWrapperX = new MPJLambdaWrapperX<>();
        mpjLambdaWrapperX
                .selectAll(CureFollowUpInfo.class)
                .selectCollection(CureFollowUpInfoDetails.class, CureFollowUpInfo::getCureFollowUpInfoDetails)
                .leftJoin(CureFollowUpInfoDetails.class, CureFollowUpInfoDetails::getFollowUpInfoId, CureFollowUpInfo::getId)
                .eq(CureFollowUpInfo::getMedicalRecordId, id);
        return selectJoinOne(CureFollowUpInfo.class, mpjLambdaWrapperX);
    }

    default PageResult<CureFollowUpInfo> getFollowUpInfoByPatientId(CureFollowUpInfoDTO cureFollowUpInfo) {
        MPJLambdaWrapper<CureFollowUpInfo> cureFollowUpInfoMPJLambdaWrapper = new MPJLambdaWrapperX<CureFollowUpInfo>()
                .selectAll(CureFollowUpInfo.class)
                .selectCollection(CureFollowUpInfoDetails.class, CureFollowUpInfo::getCureFollowUpInfoDetails)
                .leftJoin(CureFollowUpInfoDetails.class, CureFollowUpInfoDetails::getFollowUpInfoId, CureFollowUpInfo::getId)
                .eq(CureFollowUpInfo::getPatientId, cureFollowUpInfo.getPatientId())
                .eqIfExists(CureFollowUpInfo::getConfirmStatus, cureFollowUpInfo.getConfirmStatus());
        //判断是否有开始时间和结束时间
        if (cureFollowUpInfo.getCreationDate() != null) {
            String s = cureFollowUpInfo.getCreationDate().get(0);
            String e = cureFollowUpInfo.getCreationDate().get(1);
            cureFollowUpInfoMPJLambdaWrapper.between(CureFollowUpInfo::getCreationDate, s, e);
        }
        return selectJoinPage(cureFollowUpInfo, CureFollowUpInfo.class, cureFollowUpInfoMPJLambdaWrapper);
    }


    default CureFollowUpInfo getFollowUpInfoIsById(Long id) {
        return selectJoinOne(CureFollowUpInfo.class, new MPJLambdaWrapperX<CureFollowUpInfo>()
                .selectAll(CureFollowUpInfo.class)
                .selectCollection(CureFollowUpInfoDetails.class, CureFollowUpInfo::getCureFollowUpInfoDetails)
                .leftJoin(CureFollowUpInfoDetails.class, CureFollowUpInfoDetails::getFollowUpInfoId, CureFollowUpInfo::getId)
                .eq(CureFollowUpInfo::getId, id));
    }
}




