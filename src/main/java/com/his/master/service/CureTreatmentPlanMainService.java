package com.his.master.service;

import com.his.master.model.entity.CureTreatmentPlanMain;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_treatment_plan_main】的数据库操作Service
* @createDate 2024-09-11 18:38:09
*/
public interface CureTreatmentPlanMainService extends IService<CureTreatmentPlanMain> {

    /**
     * 根据医嘱id查询治疗方案
     */
    CureTreatmentPlanMain getTreatmentPlanByMedicalOrderId(Long medicalOrderId);
}
