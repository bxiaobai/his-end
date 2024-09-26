package com.his.master.controller.appointment;

import cn.hutool.core.date.DateUtil;
import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.dto.cure.DeductionSourceDTO;
import com.his.master.model.entity.AppointmentRoom;
import com.his.master.model.entity.AppointmentSource;
import com.his.master.model.vo.cure.SourceVO;
import com.his.master.service.AppointmentSourceService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointment/source")
public class AppointmentSourceController {

    @Resource
    private AppointmentSourceService appointmentSourceService;

    @PostMapping("/list")
    @ApiOperation(value = "查询号源列表")
    public BaseResponse<List<SourceVO>> pageSource(@RequestBody DeductionSourceDTO appointmentSource) {
        List<SourceVO> sourceVOList = appointmentSourceService.pageSource(appointmentSource);
        return ResultUtils.success(sourceVOList);
    }

    /**
     * 查询座位列表
     */
    @PostMapping("/seatList")
    @ApiOperation(value = "查询座位列表")
    public BaseResponse<List<AppointmentRoom>> getSeatList(@RequestBody DeductionSourceDTO appointmentSource) {
        return ResultUtils.success(appointmentSourceService.getSeatList(appointmentSource));
    }
}
