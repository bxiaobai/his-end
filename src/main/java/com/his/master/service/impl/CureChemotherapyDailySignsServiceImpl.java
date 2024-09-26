package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureChemotherapyDailySigns;
import com.his.master.service.CureChemotherapyDailySignsService;
import com.his.master.mapper.CureChemotherapyDailySignsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【cure_chemotherapy_daily_signs】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CureChemotherapyDailySignsServiceImpl extends ServiceImpl<CureChemotherapyDailySignsMapper, CureChemotherapyDailySigns>
    implements CureChemotherapyDailySignsService{

    @Override
    public boolean addChemotherapyDailySigns(CureChemotherapyDailySigns cure) {
        return save(cure);
    }

    @Override
    public boolean deleteChemotherapyDailySigns(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateChemotherapyDailySigns(CureChemotherapyDailySigns cure) {
        return updateById(cure);
    }
}




