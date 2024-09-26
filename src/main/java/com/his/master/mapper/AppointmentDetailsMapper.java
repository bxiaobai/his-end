package com.his.master.mapper;

import com.his.master.model.dto.appointment.QueryAppointmentDTO;
import com.his.master.model.dto.cure.DeductionSourceDTO;
import com.his.master.model.entity.AppointmentDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.utils.PageResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【appointment_details(预约信息表)】的数据库操作Mapper
 * @createDate 2024-09-19 10:48:25
 * @Entity com.his.master.model.entity.AppointmentDetails
 */
public interface AppointmentDetailsMapper extends BaseMapperX<AppointmentDetails> {

    default PageResult<AppointmentDetails> pageAppointment(QueryAppointmentDTO appointmentDetails) {
        return selectPage(appointmentDetails, new LambdaQueryWrapperX<AppointmentDetails>()
                .eqIfPresent(AppointmentDetails::getAppointDate, appointmentDetails.getAppointDate())
                .eqIfPresent(AppointmentDetails::getStatus, appointmentDetails.getStatus())
                .likeIfPresent(AppointmentDetails::getCardNumber, appointmentDetails.getCardNumber())
                .likeIfPresent(AppointmentDetails::getAppointee, appointmentDetails.getAppointee())
                .eqIfPresent(AppointmentDetails::getNextTreatTime, appointmentDetails.getNextTreatTime()));
    }

    /**
     * 根据条件查询count
     */
    default List<AppointmentDetails> getAppointmentCount(DeductionSourceDTO appointmentDetails) {
        String s = "";
        if (appointmentDetails.getTimeId() != null){
             s = String.join(",", appointmentDetails.getTimeId());
        }
        return selectList(new LambdaQueryWrapperX<AppointmentDetails>()
                .eqIfPresent(AppointmentDetails::getAppointDate, appointmentDetails.getDate())
                .eqIfPresent(AppointmentDetails::getSeatId, appointmentDetails.getSeatId())
                .likeIfPresent(AppointmentDetails::getAppointTime, s)
                .eqIfPresent(AppointmentDetails::getInfusionId, appointmentDetails.getRoomId())
        );
    }
}




