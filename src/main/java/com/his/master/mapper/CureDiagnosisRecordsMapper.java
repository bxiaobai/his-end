package com.his.master.mapper;

import com.his.master.model.entity.CureDiagnosisRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.LambdaQueryWrapperX;

/**
* @author Administrator
* @description 针对表【cure_diagnosis_records】的数据库操作Mapper
* @createDate 2024-09-06 15:26:33
* @Entity com.his.master.model.entity.CureDiagnosisRecords
*/
public interface CureDiagnosisRecordsMapper extends BaseMapper<CureDiagnosisRecords> {

    /**
     * 根据医疗id查询诊断结果
     */
    default CureDiagnosisRecords getCureDiagnosisRecords(Long medicalId) {
        return selectOne(new LambdaQueryWrapperX<CureDiagnosisRecords>()
                .eqIfPresent(CureDiagnosisRecords::getMedicalRecordId, medicalId));
    }
}




