package com.his.master.service;

import com.his.master.model.entity.CureDiagnosisRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_diagnosis_records】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureDiagnosisRecordsService extends IService<CureDiagnosisRecords> {

    /**
     * 添加诊断记录
     */
    boolean addDiagnosis(CureDiagnosisRecords cureDiagnosisRecords);

    /**
     * 删除诊断记录
     * @param id
     * @return
     */
    boolean deleteDiagnosis(Long id);

    /**
     * 修改诊断记录
     * @param cureDiagnosisRecords
     * @return
     */
    boolean updateDiagnosis(CureDiagnosisRecords cureDiagnosisRecords);

    boolean isExist(Long medicalRecordId);
}
