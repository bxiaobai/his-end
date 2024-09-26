package com.his.master.mapper;

import com.his.master.model.entity.AppointmentSeat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.utils.PageResult;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_seat(座位表)】的数据库操作Mapper
* @createDate 2024-09-13 10:37:28
* @Entity com.his.master.model.entity.AppointmentSeat
*/
public interface AppointmentSeatMapper extends BaseMapperX<AppointmentSeat> {

    default PageResult<AppointmentSeat> pageSeat(AppointmentSeat appointmentSeat) {
        return selectPage(appointmentSeat,new LambdaQueryWrapperX<AppointmentSeat>()
                .eqIfPresent(AppointmentSeat::getTransfusionId, appointmentSeat.getTransfusionId())
                .likeIfPresent(AppointmentSeat::getSeatNumber, appointmentSeat.getSeatNumber())
                .eqIfPresent(AppointmentSeat::getStatus, appointmentSeat.getStatus()));
    }

    default List<AppointmentSeat> getRoomSeatList(Long transfusionId) {
        return selectList(new LambdaQueryWrapperX<AppointmentSeat>()
                .eq(AppointmentSeat::getTransfusionId, transfusionId));
    }
}




