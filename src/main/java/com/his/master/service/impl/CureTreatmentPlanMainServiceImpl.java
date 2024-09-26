package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureTreatmentPlanMain;
import com.his.master.service.CureTreatmentPlanMainService;
import com.his.master.mapper.CureTreatmentPlanMainMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【cure_treatment_plan_main】的数据库操作Service实现
* @createDate 2024-09-11 18:38:09
*/
@Service
public class CureTreatmentPlanMainServiceImpl extends ServiceImpl<CureTreatmentPlanMainMapper, CureTreatmentPlanMain>
    implements CureTreatmentPlanMainService{

    @Resource
    private CureTreatmentPlanMainMapper cureTreatmentPlanMainMapper;

    @Override
    public CureTreatmentPlanMain getTreatmentPlanByMedicalOrderId(Long medicalOrderId) {
        return cureTreatmentPlanMainMapper.getCureTreatmentPlanMain(medicalOrderId);
    }
}




