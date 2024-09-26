package com.his.master.service;

import com.his.master.model.dto.appointment.AddAppointmentDTO;
import com.his.master.model.entity.AppointmentDrug;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_drug(预约药品记录)】的数据库操作Service
* @createDate 2024-09-19 10:48:44
*/
public interface AppointmentDrugService extends IService<AppointmentDrug> {

    /**
     * 根据预约id查询预约药品记录
     */
    String getAppointmentDrugByAppointmentId(Long id);

    /**
     * 添加预约药品
     */
    boolean addAppointmentDrug(AddAppointmentDTO appointmentDrug ,Long id);
}
