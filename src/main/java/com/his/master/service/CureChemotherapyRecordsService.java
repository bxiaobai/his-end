package com.his.master.service;

import com.his.master.model.entity.CureChemotherapyRecords;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_chemotherapy_records】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureChemotherapyRecordsService extends IService<CureChemotherapyRecords> {

    boolean addChemotherapy(CureChemotherapyRecords cureChemotherapyRecords);

    boolean deleteChemotherapy(Long id);

    boolean updateChemotherapy(CureChemotherapyRecords cureChemotherapyRecords);

    boolean isExist(Long medicalRecordId);
}
