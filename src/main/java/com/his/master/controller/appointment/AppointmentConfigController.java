package com.his.master.controller.appointment;

import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.dto.cure.AppointmentAddDTO;
import com.his.master.model.dto.cure.AppointmentConfigDTO;
import com.his.master.model.dto.cure.DeptConfigDTO;
import com.his.master.model.entity.AppointmentConfig;
import com.his.master.service.AppointmentConfigService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/appointment/config")
public class AppointmentConfigController {

    @Resource
    private AppointmentConfigService appointmentConfigService;

    @PostMapping("/page")
    @ApiOperation(value = "分页查询号源配置")
    public BaseResponse<PageResult<AppointmentConfig>> pageConfig(@RequestBody AppointmentConfigDTO appointmentConfig){
        return ResultUtils.success(appointmentConfigService.pageConfig(appointmentConfig));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增号源配置")
    public BaseResponse<Boolean> addConfig(@RequestBody AppointmentConfig appointmentConfig){
        return ResultUtils.success(appointmentConfigService.addConfig(appointmentConfig));
    }


    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除号源配置")
    public BaseResponse<Boolean> deleteConfig(@PathVariable Long id){
        return ResultUtils.success(appointmentConfigService.deleteConfig(id));
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改号源配置")
    public BaseResponse<Boolean> updateConfig(@RequestBody AppointmentConfig appointmentConfig){
        return ResultUtils.success(appointmentConfigService.updateConfig(appointmentConfig));
    }


    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询详情")
    public BaseResponse<AppointmentConfig> getConfigById(@PathVariable Long id){
        return ResultUtils.success(appointmentConfigService.getConfigById(id));
    }

    @PostMapping("/generateSource")
    @ApiOperation(value = "生成号源")
    public BaseResponse<Boolean> generateConfig(@RequestBody AppointmentAddDTO appointmentAddDTO){
        List<String> dataList = appointmentAddDTO.getDataList();
        Long deptId = appointmentAddDTO.getDeptId();
        return ResultUtils.success(appointmentConfigService.generateConfig(deptId,dataList));
    }

    @PostMapping("/allocateConfig")
    @ApiOperation(value = "给科室分配")
    public BaseResponse<Boolean> allocateConfig(@RequestBody List<DeptConfigDTO>  configDTO){
        boolean b = appointmentConfigService.allocateConfig(configDTO);
        return ResultUtils.success(b);
    }
}
