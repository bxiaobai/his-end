package com.his.master.service;

import com.his.master.model.entity.CureMedicalDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_medical_details】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureMedicalDetailsService extends IService<CureMedicalDetails> {

    /**
     * 添加治疗详情
     */
    boolean addTreatment(CureMedicalDetails cureMedicalDetails);

    /**
     * 修改治疗详情
     */
    boolean updateTreatment(CureMedicalDetails cureMedicalDetails);

    /**
     * 删除治疗详情
     */
    boolean deleteTreatment(Long id);

    /**
     * 查询该记录是否已有详细记录
     */
    boolean isExist(Long medicalRecordId);
}
