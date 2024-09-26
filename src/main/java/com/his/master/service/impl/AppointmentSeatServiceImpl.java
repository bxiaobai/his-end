package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.model.entity.AppointmentSeat;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.AppointmentSeatService;
import com.his.master.mapper.AppointmentSeatMapper;
import com.his.master.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.RegEx;
import javax.annotation.Resource;
import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_seat(座位表)】的数据库操作Service实现
* @createDate 2024-09-13 10:37:28
*/
@Service
public class AppointmentSeatServiceImpl extends ServiceImpl<AppointmentSeatMapper, AppointmentSeat>
    implements AppointmentSeatService{

    @Resource
    private AppointmentSeatMapper appointmentSeatMapper;

    @Override
    public PageResult<AppointmentSeat> pageSeat(AppointmentSeat appointmentSeat) {
        return appointmentSeatMapper.pageSeat(appointmentSeat);
    }

    @Override
    public Boolean addSeat(AppointmentSeat appointmentSeat) {
        //根据输液室id查询是否有相同的座位号
        AppointmentSeat one = getOne(
                new LambdaQueryWrapperX<AppointmentSeat>()
                        .eq(AppointmentSeat::getTransfusionId, appointmentSeat.getTransfusionId())
                        .eq(AppointmentSeat::getSeatNumber, appointmentSeat.getSeatNumber()));
        if (one != null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该座位号已存在");
        }
        return save(appointmentSeat);
    }

    @Override
    public Boolean deleteSeat(Long id) {
        return removeById(id);
    }

    @Override
    public Boolean updateSeat(AppointmentSeat appointmentSeat) {
        return updateById(appointmentSeat);
    }

    @Override
    public AppointmentSeat getSeatById(Long id) {
        return getById(id);
    }

    @Override
    public List<AppointmentSeat> getRoomSeatList(Long id) {
        return appointmentSeatMapper.getRoomSeatList(id);
    }
}




