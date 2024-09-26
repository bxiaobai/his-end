package com.his.master.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.his.master.model.dto.appointment.AddAppointmentDTO;
import com.his.master.model.entity.AppointmentDrug;
import com.his.master.model.entity.AppointmentDrugDetails;
import com.his.master.model.webservice.TransfusionVO;
import com.his.master.model.webservice.Yzxxs;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.AppointmentDetailsService;
import com.his.master.service.AppointmentDrugDetailsService;
import com.his.master.service.AppointmentDrugService;
import com.his.master.mapper.AppointmentDrugMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【appointment_drug(预约药品记录)】的数据库操作Service实现
* @createDate 2024-09-19 10:48:44
*/
@Service
public class AppointmentDrugServiceImpl extends ServiceImpl<AppointmentDrugMapper, AppointmentDrug>
    implements AppointmentDrugService{

    @Resource
    private AppointmentDrugDetailsService appointmentDrugDetailsService;

    @Override
    public String getAppointmentDrugByAppointmentId(Long id) {
        AppointmentDrug one = getOne(new LambdaQueryWrapperX<AppointmentDrug>().eq(AppointmentDrug::getResId, id));
        String infusionApplyNos = one.getInfusionApplyNos();
        Gson gson = new Gson();
        return gson.toJson(infusionApplyNos.split("\\$"));
    }

    @Override
    @Transactional
    public boolean addAppointmentDrug(AddAppointmentDTO addAppointmentDTO,Long id) {
        List<TransfusionVO> transfusionVOS = addAppointmentDTO.getTransfusionVOS();
        AppointmentDrug appointmentDrug = new AppointmentDrug();
        //登记时间
        appointmentDrug.setDjsj(addAppointmentDTO.getAppointDate());
        //创建医生
        String createDoctor = transfusionVOS.get(0).getCreateDoctor();
        appointmentDrug.setCreateDoctor(createDoctor);
        StringBuilder stringBuilder = new StringBuilder();
        appointmentDrug.setResId(id);
        //拼接输液单号
        for (TransfusionVO transfusionVO : transfusionVOS){
            String $ = transfusionVO.getYzxxsList().stream().map(Yzxxs::getSydh).collect(Collectors.joining("$"));
            stringBuilder.append($).append("$");
        }
        appointmentDrug.setInfusionApplyNos(stringBuilder.toString());
        //添加输液单主表
        save(appointmentDrug);
        //添加输液单子表
        List<AppointmentDrugDetails> list = new ArrayList<>();
        for (TransfusionVO transfusionVO : transfusionVOS){
            List<Yzxxs> yzxxsList = transfusionVO.getYzxxsList();
            for (Yzxxs yzxx : yzxxsList){
                AppointmentDrugDetails appointmentDrugDetails = new AppointmentDrugDetails();
                BeanUtil.copyProperties(yzxx, appointmentDrugDetails,true);
                appointmentDrugDetails.setDrugId(appointmentDrug.getDrugId());
                list.add(appointmentDrugDetails);
            }
        }
        appointmentDrugDetailsService.saveBatch(list);
        return true;
    }
}




