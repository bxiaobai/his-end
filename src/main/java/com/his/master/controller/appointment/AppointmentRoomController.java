package com.his.master.controller.appointment;

import com.his.master.common.BaseResponse;
import com.his.master.common.PageRequest;
import com.his.master.common.ResultUtils;
import com.his.master.model.entity.AppointmentRoom;
import com.his.master.service.AppointmentRoomService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/appointment/room")
@Api("大厅服务")
public class AppointmentRoomController {

    @Resource
    private AppointmentRoomService appointmentRoomService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询大厅")
    public BaseResponse<PageResult<AppointmentRoom>> pageRoom(@RequestBody AppointmentRoom appointmentRoom){
        return ResultUtils.success(appointmentRoomService.pageRoom(appointmentRoom));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增大厅")
    public BaseResponse<Boolean> addRoom(@RequestBody AppointmentRoom appointmentRoom){
        return ResultUtils.success(appointmentRoomService.addRoom(appointmentRoom));
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除大厅")
    public BaseResponse<Boolean> deleteRoom(@PathVariable Long id){
        return ResultUtils.success(appointmentRoomService.deleteRoom(id));
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改大厅")
    public BaseResponse<Boolean> updateRoom(@RequestBody AppointmentRoom appointmentRoom){
        return ResultUtils.success(appointmentRoomService.updateRoom(appointmentRoom));
    }


    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询详情")
    public BaseResponse<AppointmentRoom> getRoomById(@PathVariable Long id){
        return ResultUtils.success(appointmentRoomService.getRoomById(id));
    }

    @GetMapping("/get/roomListALl")
    @ApiOperation(value = "查询大厅座位列表")
    public BaseResponse<List<AppointmentRoom>> getAll(){
        return ResultUtils.success(appointmentRoomService.list());
    }


}
