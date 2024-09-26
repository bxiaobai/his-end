package com.his.master.controller.appointment;

import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.dto.appointment.AddAppointmentDTO;
import com.his.master.model.dto.appointment.QueryAppointmentDTO;
import com.his.master.model.dto.cure.DeductionSourceDTO;
import com.his.master.model.entity.AppointmentDetails;
import com.his.master.model.vo.appointment.BaseInfo.BaseInfo;
import com.his.master.model.vo.cure.SourceVO;
import com.his.master.model.webservice.TransfusionVO;
import com.his.master.service.AppointmentDetailsService;
import com.his.master.service.AppointmentSourceService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/appointment/details")
public class AppointmentDetailsController {

    @Resource
    private AppointmentDetailsService appointmentDetailsService;

    /**
     * 查询患者医嘱信息
     */
    @GetMapping("/getPatientInfo/{cardNo}")
    @ApiOperation(value = "查询患者医嘱信息")
    public BaseResponse<BaseInfo> getPatientInfo(@PathVariable("cardNo") String cardNo) {
        //患者医嘱信息
        BaseInfo patientInfo = appointmentDetailsService.getPatientInfo(cardNo);
        return ResultUtils.success(patientInfo);
    }

    /**
     * 添加预约信息
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加预约信息")
    public BaseResponse<Boolean> addAppointment(@RequestBody AddAppointmentDTO addAppointmentDTO) {
        return ResultUtils.success(appointmentDetailsService.addAppointment(addAppointmentDTO));
    }

    /**
     * 取消预约、人工取消
     */
    @PostMapping("/cancel/{id}")
    @ApiOperation(value = "取消预约")
    public BaseResponse<Boolean> cancelAppointment(@PathVariable("id") Long id) {
        return ResultUtils.success(appointmentDetailsService.deleteAppointment(id));
    }

    /**
     * 获取预约详情
     */
    @GetMapping("/getAppointmentById/{id}")
    @ApiOperation(value = "获取预约详情")
    public BaseResponse<AppointmentDetails> getAppointmentById(@PathVariable("id") Long id) {
        return ResultUtils.success(appointmentDetailsService.getAppointmentById(id));
    }

    /**
     * 分页查询医嘱信息
     */
    @PostMapping("/page")
    @ApiOperation(value = "分页查询医嘱信息")
    public BaseResponse<PageResult<AppointmentDetails>> pageAppointment(@RequestBody QueryAppointmentDTO appointmentDetails) {
        return ResultUtils.success(appointmentDetailsService.pageAppointment(appointmentDetails));
    }
}
