package com.his.master.controller.appointment;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.entity.AppointmentRoom;
import com.his.master.model.entity.AppointmentSeat;
import com.his.master.model.entity.AppointmentSeat;
import com.his.master.service.AppointmentSeatService;
import com.his.master.service.AppointmentSeatService;
import com.his.master.utils.DateUtils;
import com.his.master.utils.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/appointment/seat")
public class AppointmentSeatController {

    @Resource
    private AppointmentSeatService appointmentSeatService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询座位")
    public BaseResponse<PageResult<AppointmentSeat>> pageSeat(@RequestBody AppointmentSeat appointmentSeat) {
        return ResultUtils.success(appointmentSeatService.pageSeat(appointmentSeat));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增座位")
    public BaseResponse<Boolean> addSeat(@RequestBody AppointmentSeat appointmentSeat) {
        return ResultUtils.success(appointmentSeatService.addSeat(appointmentSeat));
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除座位")
    public BaseResponse<Boolean> deleteSeat(@PathVariable Long id) {
        return ResultUtils.success(appointmentSeatService.deleteSeat(id));
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改座位")
    public BaseResponse<Boolean> updateSeat(@RequestBody AppointmentSeat appointmentSeat) {
        return ResultUtils.success(appointmentSeatService.updateSeat(appointmentSeat));
    }


    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询详情")
    public BaseResponse<AppointmentSeat> getSeatById(@PathVariable Long id) {
        return ResultUtils.success(appointmentSeatService.getSeatById(id));
    }

    @GetMapping("/get/seatList/{id}")
    @ApiOperation(value = "查询输液室座位列表")
    public BaseResponse<List<AppointmentSeat>> getAll(@PathVariable Long id) {
        return ResultUtils.success(appointmentSeatService.getRoomSeatList(id));
    }


}
