package com.his.master.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.master.model.entity.AppointmentSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_source(号源表)】的数据库操作Mapper
* @createDate 2024-09-13 16:03:26
* @Entity com.his.master.model.entity.AppointmentSource
*/
public interface AppointmentSourceMapper extends BaseMapper<AppointmentSource> {

    /**
     * 查询临时号源数量
     */
    default Integer getSourceCount(Long sourceId ){
        return selectById(sourceId).getStandbyNumber();
    }

    /**
     * 查询征程号源数量
     */
    default Integer getRemainingCount(Long sourceId){
        AppointmentSource remainingCount = selectById(sourceId);
        return remainingCount.getRemainingCount();
    }

    /**
     * 根据号源类型、日期、输液室id获取号源列表
     */
    default List<AppointmentSource> getSourceList(Long roomId,  List<Date> date){
        LambdaQueryWrapper<AppointmentSource> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AppointmentSource::getTransfusionId, roomId);
        lambdaQueryWrapper.in(AppointmentSource::getConfigDate, date);
        return selectList(lambdaQueryWrapper);
    }

}




