package com.his.master.service;

import com.his.master.model.entity.CureChemoDailyMedication;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_chemo_daily_medication】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureChemoDailyMedicationService extends IService<CureChemoDailyMedication> {

    boolean addMedication(CureChemoDailyMedication cureChemoDailyMedication);

    boolean deleteMedication(Long id);

    boolean updateMedication(CureChemoDailyMedication cureChemoDailyMedication);
}
