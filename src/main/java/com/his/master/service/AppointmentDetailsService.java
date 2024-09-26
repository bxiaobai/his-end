package com.his.master.service;

import com.his.master.model.dto.appointment.AddAppointmentDTO;
import com.his.master.model.dto.appointment.QueryAppointmentDTO;
import com.his.master.model.dto.cure.DeductionSourceDTO;
import com.his.master.model.entity.AppointmentDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.model.vo.appointment.BaseInfo.BaseInfo;
import com.his.master.model.webservice.MainData;
import com.his.master.model.webservice.TransfusionVO;
import com.his.master.model.webservice.removeVO.RemoveVO;
import com.his.master.utils.PageResult;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_details(预约信息表)】的数据库操作Service
* @createDate 2024-09-19 10:48:25
*/
public interface AppointmentDetailsService extends IService<AppointmentDetails> {

    /**
     * 分页查询预约信息
     */
    PageResult<AppointmentDetails> pageAppointment(QueryAppointmentDTO appointmentDetails);

    /**
     * 添加预约
     */
    boolean addAppointment(AddAppointmentDTO addAppointmentDTO);

    /**
     * 修改预约
     * @param appointmentDetails
     * @return
     */
    boolean updateAppointment(AppointmentDetails appointmentDetails);

    /**
     * 取消取悦
     * @param id
     * @return
     */
    boolean deleteAppointment(Long id);

    /**
     * 查询预约详情
     */
    AppointmentDetails getAppointmentById(Long id);


    /**
     * 构建pdh
     */
    String buildPdh(String data, List<String> aptTimeList);

    /**
     * 根据输液单号查询医嘱信息
     */
    MainData getTransfusionInfo(String pdh);

    /**
     * 根据卡号和开始时间结束时间查询医嘱和药品
     */
    List<MainData> getTransfusionInfoByCardNo(String cardNo);

    /**
     * 取消预约webserver
     */
    boolean cancelAppointment(RemoveVO removeVO);

    /**
     * 构建短信内容
     */
    String sendGetMessage(String date, String time, String site, String phone);

    /**
     * 根据卡号获取输液信息
     * @param cardNo
     */
    BaseInfo getPatientInfo(String cardNo);

    /**
     * 根据条件查询预约信息
     */
    List<AppointmentDetails> getAppointmentCount(DeductionSourceDTO appointmentDetails);


}
