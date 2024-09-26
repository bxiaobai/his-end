package com.his.master.service;

import com.his.master.model.entity.CureVitalsignsRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_vitalsigns_records】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureVitalsignsRecordsService extends IService<CureVitalsignsRecords> {

    boolean addTreatment(CureVitalsignsRecords cureVitalsignsRecords);

    boolean deleteTreatment(Long id);

    boolean updateTreatment(CureVitalsignsRecords cureVitalsignsRecords);

    boolean isExist(Long medicalRecordId);

}
