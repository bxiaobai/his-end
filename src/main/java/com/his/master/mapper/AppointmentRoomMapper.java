package com.his.master.mapper;

import com.his.master.model.entity.AppointmentRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.utils.PageResult;

/**
* @author Administrator
* @description 针对表【appointment_room(输液室表)】的数据库操作Mapper
* @createDate 2024-09-13 10:37:28
* @Entity com.his.master.model.entity.AppointmentRoom
*/
public interface AppointmentRoomMapper extends BaseMapperX<AppointmentRoom> {

    default PageResult<AppointmentRoom> pageRoom(AppointmentRoom appointmentRoom){
        return selectPage(appointmentRoom,new LambdaQueryWrapperX<AppointmentRoom>()
                .likeIfPresent( AppointmentRoom::getTransfusionName, appointmentRoom.getTransfusionName())
                .eqIfPresent(AppointmentRoom::getStatus, appointmentRoom.getStatus()));
    }
}




