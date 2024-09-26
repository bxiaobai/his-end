package com.his.master.mapper;

import com.his.master.model.entity.CureMedicalRecords;
import com.his.master.model.entity.CureTreatmentPlanDetails;
import com.his.master.model.entity.CureTreatmentPlanMain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.model.entity.CureTreatmentPlanMedicineDetails;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;

/**
 * @author Administrator
 * @description 针对表【cure_treatment_plan_main】的数据库操作Mapper
 * @createDate 2024-09-11 18:38:09
 * @Entity com.his.master.model.entity.CureTreatmentPlanMain
 */
public interface CureTreatmentPlanMainMapper extends BaseMapperX<CureTreatmentPlanMain> {

    default CureTreatmentPlanMain getCureTreatmentPlanMain(Long id) {
        MPJLambdaWrapperX<CureTreatmentPlanMain> lambdaQueryWrapperX = new MPJLambdaWrapperX<>();
        lambdaQueryWrapperX
                .selectAll(CureTreatmentPlanMain.class)
                .selectCollection(CureTreatmentPlanDetails.class, CureTreatmentPlanMain::getCureTreatmentPlanDetails
                        , b -> b.collection(CureTreatmentPlanMedicineDetails.class, CureTreatmentPlanDetails::getCureTreatmentPlanMedicineDetails)
                )
                .leftJoin(CureTreatmentPlanDetails.class, CureTreatmentPlanDetails::getMedicalOrderId, CureTreatmentPlanMain::getId)
                .leftJoin(CureTreatmentPlanMedicineDetails.class, CureTreatmentPlanMedicineDetails::getDetailId, CureTreatmentPlanDetails::getId)
                .eq(CureTreatmentPlanMain::getId, id);
        return selectJoinOne(CureTreatmentPlanMain.class, lambdaQueryWrapperX);
    }
}




