package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.model.entity.AppointmentRoom;
import com.his.master.model.entity.AppointmentSeat;
import com.his.master.service.AppointmentRoomService;
import com.his.master.mapper.AppointmentRoomMapper;
import com.his.master.service.AppointmentSeatService;
import com.his.master.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_room(输液室表)】的数据库操作Service实现
* @createDate 2024-09-13 10:37:28
*/
@Service
public class AppointmentRoomServiceImpl extends ServiceImpl<AppointmentRoomMapper, AppointmentRoom>
    implements AppointmentRoomService{

    @Resource
    private AppointmentRoomMapper appointmentRoomMapper;

    @Resource
    private AppointmentSeatService appointmentSeatService;

    @Override
    public boolean addRoom(AppointmentRoom appointmentRoom) {
        if (appointmentRoom.getMedicalType() == null){
            appointmentRoom.setMedicalType("1");
        }
        return save(appointmentRoom);
    }

    @Override
    public boolean deleteRoom(Long id) {
        //判断输液室下面有没有座位
        List<AppointmentSeat> roomSeatList = appointmentSeatService.getRoomSeatList(id);
        if (!roomSeatList.isEmpty()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请先删除座位");
        }
        return removeById(id);
    }

    @Override
    public boolean updateRoom(AppointmentRoom appointmentRoom) {
        return updateById(appointmentRoom);
    }

    @Override
    public AppointmentRoom getRoomById(Long id) {
        // 根据输液室查询座位信息
        AppointmentRoom byId = getById(id);
        if (byId != null){
            List<AppointmentSeat> roomSeatList = appointmentSeatService.getRoomSeatList(id);
            byId.setAppointmentSeats(roomSeatList);
        }
        return byId;
    }

    @Override
    public PageResult<AppointmentRoom> pageRoom(AppointmentRoom appointmentRoom) {
        return  appointmentRoomMapper.pageRoom(appointmentRoom);
    }


}




