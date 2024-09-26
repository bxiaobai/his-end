package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureDiagnosisRecords;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureDiagnosisRecordsService;
import com.his.master.mapper.CureDiagnosisRecordsMapper;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Administrator
* @description 针对表【cure_diagnosis_records】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CureDiagnosisRecordsServiceImpl extends ServiceImpl<CureDiagnosisRecordsMapper, CureDiagnosisRecords>
    implements CureDiagnosisRecordsService{


    @Override
    public boolean addDiagnosis(CureDiagnosisRecords cureDiagnosisRecords) {
        //获取登陆人姓名
        String staffCode = SecurityUtils.getUser().getStaffCode();
        cureDiagnosisRecords.setCreator(staffCode);
        cureDiagnosisRecords.setCreationDate(new Date());
        return save(cureDiagnosisRecords);
    }

    @Override
    public boolean deleteDiagnosis(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateDiagnosis(CureDiagnosisRecords cureDiagnosisRecords) {
        return updateById(cureDiagnosisRecords);
    }

    @Override
    public boolean isExist(Long medicalRecordId) {
        return count(new LambdaQueryWrapperX<CureDiagnosisRecords>()
                .eq(CureDiagnosisRecords::getMedicalRecordId,medicalRecordId)) == 0;
    }


}




