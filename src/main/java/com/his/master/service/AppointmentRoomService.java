package com.his.master.service;

import com.his.master.model.entity.AppointmentRoom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.utils.PageResult;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_room(输液室表)】的数据库操作Service
* @createDate 2024-09-13 10:37:28
*/
public interface AppointmentRoomService extends IService<AppointmentRoom> {

    boolean addRoom(AppointmentRoom appointmentRoom);

    boolean deleteRoom(Long id);

    boolean updateRoom(AppointmentRoom appointmentRoom);

    AppointmentRoom getRoomById(Long id);

    PageResult<AppointmentRoom> pageRoom(AppointmentRoom appointmentRoom);



}
