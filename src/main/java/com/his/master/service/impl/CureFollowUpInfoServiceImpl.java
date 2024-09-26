package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.dto.cure.CureFollowUpInfoDTO;
import com.his.master.model.entity.CureFollowUpInfo;
import com.his.master.model.entity.CureFollowUpInfoDetails;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureFollowUpInfoDetailsService;
import com.his.master.service.CureFollowUpInfoService;
import com.his.master.mapper.CureFollowUpInfoMapper;
import com.his.master.utils.PageResult;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【cure_follow_up_info】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CureFollowUpInfoServiceImpl extends ServiceImpl<CureFollowUpInfoMapper, CureFollowUpInfo>
    implements CureFollowUpInfoService{

    @Resource
    private CureFollowUpInfoDetailsService cureFollowUpInfoDetailsServiceService;

    @Resource
    private CureFollowUpInfoMapper cureFollowUpInfoMapper;

    @Override
    public boolean addFollowUpInfo(CureFollowUpInfo cureFollowUpInfo) {
        String staffCode = SecurityUtils.getUser().getStaffCode();
        cureFollowUpInfo.setCreator(staffCode);
        cureFollowUpInfo.setCreationDate(new Date());
        save(cureFollowUpInfo);
        //添加随访详情
        List<CureFollowUpInfoDetails> cureFollowUpInfoDetails = cureFollowUpInfo.getCureFollowUpInfoDetails();
        if (!cureFollowUpInfoDetails.isEmpty()){
            addFollowUpInfoDetails(cureFollowUpInfoDetails,cureFollowUpInfo.getId());
        }
        return true;
    }

    @Override
    public boolean deleteFollowUpInfo(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateFollowUpInfo(CureFollowUpInfo cureFollowUpInfo) {
        return updateById(cureFollowUpInfo);
    }

    @Override
    public boolean isExist(Long medicalRecordId) {
        return count(new LambdaQueryWrapperX<CureFollowUpInfo>().eq(CureFollowUpInfo::getMedicalRecordId, medicalRecordId)) ==0;
    }

    @Override
    public PageResult<CureFollowUpInfo> getFollowUpInfo(CureFollowUpInfoDTO cureFollowUpInfo) {
        return cureFollowUpInfoMapper.getFollowUpInfoByPatientId(cureFollowUpInfo);
    }

    @Override
    public CureFollowUpInfo getCureFollowUpInfoById(Long id) {
        return cureFollowUpInfoMapper.getFollowUpInfoIsById(id);
    }

    /**
     * 添加随访详情
     */
    private void addFollowUpInfoDetails(List<CureFollowUpInfoDetails> cureFollowUpInfo , Long id) {
        for (CureFollowUpInfoDetails cureFollowUpInfoDetails : cureFollowUpInfo){
            cureFollowUpInfoDetails.setFollowUpInfoId(id);
            cureFollowUpInfoDetailsServiceService.addFollowUpInfoDetails(cureFollowUpInfoDetails);
        }
    }
}




