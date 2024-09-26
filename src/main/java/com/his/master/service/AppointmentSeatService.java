package com.his.master.service;

import com.his.master.model.entity.AppointmentSeat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.utils.PageResult;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_seat(座位表)】的数据库操作Service
* @createDate 2024-09-13 10:37:28
*/
public interface AppointmentSeatService extends IService<AppointmentSeat> {

    PageResult<AppointmentSeat> pageSeat(AppointmentSeat appointmentSeat);

    Boolean addSeat(AppointmentSeat appointmentSeat);

    Boolean deleteSeat(Long id);

    Boolean updateSeat(AppointmentSeat appointmentSeat);

    AppointmentSeat getSeatById(Long id);

    List<AppointmentSeat> getRoomSeatList(Long id);
}
