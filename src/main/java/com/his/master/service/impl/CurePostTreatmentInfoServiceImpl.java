package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureAdverseReactions;
import com.his.master.model.entity.CurePostTreatmentInfo;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureAdverseReactionsService;
import com.his.master.service.CurePostTreatmentInfoService;
import com.his.master.mapper.CurePostTreatmentInfoMapper;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【cure_post_treatment_info】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CurePostTreatmentInfoServiceImpl extends ServiceImpl<CurePostTreatmentInfoMapper, CurePostTreatmentInfo>
    implements CurePostTreatmentInfoService{

    @Resource
    private CureAdverseReactionsService cureAdverseReactionsService;
    @Override
    @Transactional
    public boolean addPostTreatment(CurePostTreatmentInfo curePostTreatmentInfo) {
        String staffCode = SecurityUtils.getUser().getStaffCode();
        curePostTreatmentInfo.setCreator(staffCode);
        curePostTreatmentInfo.setCreationDate(new Date());
        save(curePostTreatmentInfo);
        Long id = curePostTreatmentInfo.getId();
        //添加不良反应表
        addAdverseReactions(curePostTreatmentInfo.getCureAdverseReactions(),id);
        return true;
    }

    @Override
    public boolean deletePostTreatment(Long id) {
        return false;
    }

    @Override
    public boolean updatePostTreatment(CurePostTreatmentInfo curePostTreatmentInfo) {
        return false;
    }

    @Override
    public boolean isExist(Long medicalRecordId) {
        return count(new LambdaQueryWrapperX<CurePostTreatmentInfo>()
                .eq(CurePostTreatmentInfo::getMedicalRecordId, medicalRecordId)) == 0;
    }

    private void addAdverseReactions(List<CureAdverseReactions> cureAdverseReactions , Long id ) {
        for (CureAdverseReactions curePost : cureAdverseReactions ){
            curePost.setPostTreatmentInfoId(id);
            cureAdverseReactionsService.save(curePost);
        }
    }
}




