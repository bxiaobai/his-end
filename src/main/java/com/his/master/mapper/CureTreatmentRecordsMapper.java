package com.his.master.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.model.dto.cure.CureTreatmentRecordsDTO;
import com.his.master.model.entity.CureTreatmentRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.utils.PageResult;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【cure_treatment_records】的数据库操作Mapper
 * @createDate 2024-09-11 18:38:09
 * @Entity com.his.master.model.entity.CureTreatmentRecords
 */
public interface CureTreatmentRecordsMapper extends BaseMapperX<CureTreatmentRecords> {

    default PageResult<CureTreatmentRecords> listTreatmentRecords(CureTreatmentRecordsDTO cureTreatmentRecords) {
        List<String> creationDate = cureTreatmentRecords.getCreationDate();
        LambdaQueryWrapperX<CureTreatmentRecords> cureTreatmentRecordsLambdaQueryWrapperX = new LambdaQueryWrapperX<CureTreatmentRecords>()
                .eq(CureTreatmentRecords::getPatientId, cureTreatmentRecords.getPatientId())
                .eqIfPresent(CureTreatmentRecords::getTreatmentStatus, cureTreatmentRecords.getTreatmentStatus());
        if (creationDate != null) {
            String s = cureTreatmentRecords.getCreationDate().get(0);
            String e = cureTreatmentRecords.getCreationDate().get(1);
            cureTreatmentRecordsLambdaQueryWrapperX
                    .betweenIfPresent(CureTreatmentRecords::getCreationDate, s, e);
        }
        return selectPage(cureTreatmentRecords, cureTreatmentRecordsLambdaQueryWrapperX);
    }
}




