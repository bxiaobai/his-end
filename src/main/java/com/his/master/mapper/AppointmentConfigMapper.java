package com.his.master.mapper;

import com.his.master.model.dto.cure.AppointmentConfigDTO;
import com.his.master.model.entity.AppointmentConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.model.entity.AppointmentConfigDept;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.utils.PageResult;

import java.util.Date;

/**
 * @author Administrator
 * @description 针对表【appointment_config(号源模板主表)】的数据库操作Mapper
 * @createDate 2024-09-13 16:03:26
 * @Entity com.his.master.model.entity.AppointmentConfig
 */
public interface AppointmentConfigMapper extends BaseMapperX<AppointmentConfig> {

    default PageResult<AppointmentConfig> pageConfig(AppointmentConfigDTO appointmentConfig) {
        String startTime = appointmentConfig.getStartTime();
        String endTime = appointmentConfig.getEndTime();
        LambdaQueryWrapperX<AppointmentConfig> lambdaQueryWrapperX = new LambdaQueryWrapperX<AppointmentConfig>()
                .eqIfPresent(AppointmentConfig::getTimeClass, appointmentConfig.getTimeClass())
                .eqIfPresent(AppointmentConfig::getStatus, appointmentConfig.getStatus())
                .eqIfPresent(AppointmentConfig::getStartTime , startTime)
                .eqIfPresent(AppointmentConfig::getEndTime , endTime);
        return selectPage(appointmentConfig,lambdaQueryWrapperX);

    }


}




