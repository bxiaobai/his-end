package com.his.master.service;

import com.his.master.model.entity.CureChemotherapyDailySigns;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_chemotherapy_daily_signs】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureChemotherapyDailySignsService extends IService<CureChemotherapyDailySigns> {

    boolean addChemotherapyDailySigns(CureChemotherapyDailySigns cure);

    boolean deleteChemotherapyDailySigns(Long id);

    boolean updateChemotherapyDailySigns(CureChemotherapyDailySigns cure);


}
