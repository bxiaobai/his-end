package com.his.master.service;

import com.his.master.model.entity.MedPatientTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【med_patient_tag(患者标签映射表)】的数据库操作Service
* @createDate 2024-07-24 14:35:11
*/
public interface MedPatientTagService extends IService<MedPatientTag> {

    /**
     * 根据患者id获取使用的标签
     */
    List<MedPatientTag> getUsedTags(Long patientId);
}
