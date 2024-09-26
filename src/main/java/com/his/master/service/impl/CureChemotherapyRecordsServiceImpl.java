package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.mapper.CureChemoDailyMedicationMapper;
import com.his.master.mapper.CureChemotherapyDailySignsMapper;
import com.his.master.model.entity.CureChemoDailyMedication;
import com.his.master.model.entity.CureChemotherapyDailySigns;
import com.his.master.model.entity.CureChemotherapyRecords;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureChemoDailyMedicationService;
import com.his.master.service.CureChemotherapyDailySignsService;
import com.his.master.service.CureChemotherapyRecordsService;
import com.his.master.mapper.CureChemotherapyRecordsMapper;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【cure_chemotherapy_records】的数据库操作Service实现
 * @createDate 2024-09-06 15:26:33
 */
@Service
public class CureChemotherapyRecordsServiceImpl extends ServiceImpl<CureChemotherapyRecordsMapper, CureChemotherapyRecords>
        implements CureChemotherapyRecordsService {

    @Resource
    private CureChemoDailyMedicationService cureChemoDailyMedicationService;
    ;

    @Resource
    private CureChemotherapyDailySignsService cureChemotherapyDailySignsService;

    @Override
    public boolean addChemotherapy(CureChemotherapyRecords cureChemotherapyRecords) {
        //获取登陆人姓名
        String staffCode = SecurityUtils.getUser().getStaffCode();
        cureChemotherapyRecords.setCreator(staffCode);
        cureChemotherapyRecords.setCreationDate(new Date());
        //添加主表
        save(cureChemotherapyRecords);
        Long id = cureChemotherapyRecords.getId();
        //添加药品
        //药品列表
        List<CureChemoDailyMedication> medication = cureChemotherapyRecords.getCureChemoDailyMedication();
        if (!medication.isEmpty()) {
            addCureChemoDailyMedication(medication, id);
        }
        //添加当日体征
        CureChemotherapyDailySigns cureChemotherapyDailySigns = cureChemotherapyRecords.getCureChemotherapyDailySigns();
        addCureChemotherapyDailySigns(cureChemotherapyDailySigns,id);
        return true;
    }

    @Override
    public boolean deleteChemotherapy(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateChemotherapy(CureChemotherapyRecords cureChemotherapyRecords) {
        return updateById(cureChemotherapyRecords);
    }

    @Override
    public boolean isExist(Long medicalRecordId) {
        return count(new LambdaQueryWrapperX<CureChemotherapyRecords>()
                .eq(CureChemotherapyRecords::getMedicalRecordId, medicalRecordId)) == 0;
    }

    private void addCureChemoDailyMedication(List<CureChemoDailyMedication> cure, Long id) {
        for (CureChemoDailyMedication cureChemoDailyMedication : cure) {
            cureChemoDailyMedication.setChemotherapyRecordId(id);
            cureChemoDailyMedicationService.addMedication(cureChemoDailyMedication);
        }
    }

    private void addCureChemotherapyDailySigns(CureChemotherapyDailySigns cure, Long id) {
        cure.setChemotherapyRecordId(id);
        cureChemotherapyDailySignsService.addChemotherapyDailySigns(cure);
    }
}




