package com.his.master.mapper;

import com.his.master.model.entity.CureChemoDailyMedication;
import com.his.master.model.entity.CureChemotherapyDailySigns;
import com.his.master.model.entity.CureChemotherapyRecords;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;

/**
* @author Administrator
* @description 针对表【cure_chemotherapy_records】的数据库操作Mapper
* @createDate 2024-09-06 15:26:33
* @Entity com.his.master.model.entity.CureChemotherapyRecords
*/
public interface CureChemotherapyRecordsMapper extends BaseMapperX<CureChemotherapyRecords> {

    default CureChemotherapyRecords getCureChemotherapyRecordsById(Long id){
        MPJLambdaWrapperX<CureChemotherapyRecords> lambdaQueryWrapperX = new MPJLambdaWrapperX<>();
        lambdaQueryWrapperX
                .selectAll(CureChemotherapyRecords.class)
                .selectAssociation(CureChemotherapyDailySigns.class,CureChemotherapyRecords::getCureChemotherapyDailySigns)
                .selectCollection(CureChemoDailyMedication.class,CureChemotherapyRecords::getCureChemoDailyMedication)
                .leftJoin(CureChemotherapyDailySigns.class, CureChemotherapyDailySigns::getChemotherapyRecordId, CureChemotherapyRecords::getId)
                .leftJoin(CureChemoDailyMedication.class, CureChemoDailyMedication::getChemotherapyRecordId, CureChemotherapyRecords::getId)
                .eq(CureChemotherapyRecords::getMedicalRecordId,id);
       return selectJoinOne(CureChemotherapyRecords.class, lambdaQueryWrapperX);
    }
}




