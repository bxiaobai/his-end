package com.his.master.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.master.model.entity.CureMedicalDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【cure_medical_details】的数据库操作Mapper
* @createDate 2024-09-06 15:26:33
* @Entity com.his.master.model.entity.CureMedicalDetails
*/
public interface CureMedicalDetailsMapper extends BaseMapper<CureMedicalDetails> {

    default CureMedicalDetails getById(Long id){
        return selectOne(new LambdaQueryWrapper<CureMedicalDetails>().eq(CureMedicalDetails::getMedicalRecordId, id));
    }

}




