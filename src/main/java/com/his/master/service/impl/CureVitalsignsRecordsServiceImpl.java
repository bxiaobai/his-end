package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureVitalsignsRecords;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureVitalsignsRecordsService;
import com.his.master.mapper.CureVitalsignsRecordsMapper;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Administrator
 * @description 针对表【cure_vitalsigns_records】的数据库操作Service实现
 * @createDate 2024-09-06 15:26:33
 */
@Service
public class CureVitalsignsRecordsServiceImpl extends ServiceImpl<CureVitalsignsRecordsMapper, CureVitalsignsRecords>
        implements CureVitalsignsRecordsService {

    @Override
    public boolean addTreatment(CureVitalsignsRecords cureVitalsignsRecords) {
        String staffCode = SecurityUtils.getUser().getStaffCode();
        cureVitalsignsRecords.setCreator(staffCode);
        cureVitalsignsRecords.setCreationDate(new Date());
        return save(cureVitalsignsRecords);
    }

    @Override
    public boolean deleteTreatment(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateTreatment(CureVitalsignsRecords cureVitalsignsRecords) {
        return updateById(cureVitalsignsRecords);
    }

    @Override
    public boolean isExist(Long medicalRecordId) {
        return count(new LambdaQueryWrapperX<CureVitalsignsRecords>()
                .eq(CureVitalsignsRecords::getMedicalRecordId, medicalRecordId)) == 0;
    }
}




