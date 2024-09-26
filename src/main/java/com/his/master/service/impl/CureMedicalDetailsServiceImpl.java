package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureMedicalDetails;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureMedicalDetailsService;
import com.his.master.mapper.CureMedicalDetailsMapper;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author Administrator
* @description 针对表【cure_medical_details】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CureMedicalDetailsServiceImpl extends ServiceImpl<CureMedicalDetailsMapper, CureMedicalDetails>
    implements CureMedicalDetailsService{

    @Resource
    protected CureMedicalDetailsMapper cureMedicalDetailsMapper;

    @Override
    public boolean addTreatment(CureMedicalDetails cureMedicalDetails) {
        //获取登陆人id
        String  staffCode = SecurityUtils.getUser().getStaffCode();
        cureMedicalDetails.setCreator(staffCode);
        cureMedicalDetails.setCreationDate(new Date());
        return save(cureMedicalDetails);
    }

    @Override
    public boolean updateTreatment(CureMedicalDetails cureMedicalDetails) {
        //获取更新人id
        String  staffCode = SecurityUtils.getUser().getStaffCode();
        return updateById(cureMedicalDetails);
    }

    @Override
    public boolean deleteTreatment(Long id) {
        return removeById(id);
    }

    @Override
    public boolean isExist(Long medicalRecordId) {
        return count(new LambdaQueryWrapperX<CureMedicalDetails>()
                .eq(CureMedicalDetails::getMedicalRecordId, medicalRecordId)) == 0;
    }
}




