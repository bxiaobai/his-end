package com.his.master.mapper;

import com.his.master.model.entity.CureMedicalRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Administrator
 * @description 针对表【cure_medical_records】的数据库操作Mapper
 * @createDate 2024-09-06 15:26:33
 * @Entity com.his.master.model.entity.CureMedicalRecords
 */
public interface CureMedicalRecordsMapper extends BaseMapperX<CureMedicalRecords> {

    default CureMedicalRecords getByPatientIdAndTreatmentTime(Long patientId, String treatmentTime) {
        return this.selectOne(
                new LambdaQueryWrapperX<CureMedicalRecords>()
                        .eq(CureMedicalRecords::getPatientId, patientId)
                        .apply("DATE(treatment_time) = {0}" ,treatmentTime  )
        );

    }
}




