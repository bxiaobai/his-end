package com.his.master.service;

import com.his.master.model.dto.cure.DeductionSourceDTO;
import com.his.master.model.entity.AppointmentConfig;
import com.his.master.model.entity.AppointmentRoom;
import com.his.master.model.entity.AppointmentSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.model.vo.cure.SourceVO;
import com.his.master.utils.PageResult;

import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_source(号源表)】的数据库操作Service
* @createDate 2024-09-13 16:03:26
*/
public interface AppointmentSourceService extends IService<AppointmentSource> {

    /**
     * 通过配置id构建号源
     */
    AppointmentSource buildSource(AppointmentConfig appointmentConfig, Long deptId , Date date);

    /**
     * 扣减号源
     */
    boolean deductionSource(DeductionSourceDTO deductionSourceDTO);

    /**
     * 增加号源
     */
    boolean addSource(DeductionSourceDTO appointmentSource);


    /**
     * 查询号源列表
     */
    List<SourceVO> pageSource(DeductionSourceDTO appointmentSource);

    /**
     * 查询号源剩余数量
     */
    Integer getSourceCount(Long sourceId , Integer sourceType);


    List<AppointmentRoom> getSeatList(DeductionSourceDTO appointmentSource);
}
