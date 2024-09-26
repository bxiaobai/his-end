package com.his.master.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.master.model.entity.CureVitalsignsRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author Administrator
 * @description 针对表【cure_vitalsigns_records】的数据库操作Mapper
 * @createDate 2024-09-06 15:26:33
 * @Entity com.his.master.model.entity.CureVitalsignsRecords
 */
public interface CureVitalsignsRecordsMapper extends BaseMapper<CureVitalsignsRecords> {

    default CureVitalsignsRecords getCureVitalsignsRecords(Long id) {
        return selectOne(new LambdaQueryWrapper<CureVitalsignsRecords>().eq(CureVitalsignsRecords::getMedicalRecordId, id));
    }
}




