package com.his.master.service;

import com.his.master.model.entity.CurePostTreatmentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_post_treatment_info】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CurePostTreatmentInfoService extends IService<CurePostTreatmentInfo> {

    boolean addPostTreatment(CurePostTreatmentInfo curePostTreatmentInfo);

    boolean deletePostTreatment(Long id);

    boolean updatePostTreatment(CurePostTreatmentInfo curePostTreatmentInfo);

    boolean isExist(Long medicalRecordId);
}
