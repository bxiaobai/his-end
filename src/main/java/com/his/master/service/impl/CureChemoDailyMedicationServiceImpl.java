package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureChemoDailyMedication;
import com.his.master.service.CureChemoDailyMedicationService;
import com.his.master.mapper.CureChemoDailyMedicationMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【cure_chemo_daily_medication】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CureChemoDailyMedicationServiceImpl extends ServiceImpl<CureChemoDailyMedicationMapper, CureChemoDailyMedication>
    implements CureChemoDailyMedicationService{

    @Override
    public boolean addMedication(CureChemoDailyMedication cureChemoDailyMedication) {
        return save(cureChemoDailyMedication);
    }

    @Override
    public boolean deleteMedication(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateMedication(CureChemoDailyMedication cureChemoDailyMedication) {
        return updateById(cureChemoDailyMedication);
    }
}




