package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.mapper.AppointmentConfigMapper;
import com.his.master.model.entity.AppointmentConfig;
import com.his.master.model.entity.AppointmentConfigDept;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.AppointmentConfigDeptService;
import com.his.master.mapper.AppointmentConfigDeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【appointment_config_dept(号源配置科室表)】的数据库操作Service实现
 * @createDate 2024-09-13 16:03:26
 */
@Service
public class AppointmentConfigDeptServiceImpl extends ServiceImpl<AppointmentConfigDeptMapper, AppointmentConfigDept>
        implements AppointmentConfigDeptService {

    @Resource
    private AppointmentConfigMapper appointmentConfigMapper;

    @Resource
    private AppointmentConfigDeptMapper appointmentConfigDeptMapper;

    @Override
    public boolean isExistDeptConfig(Long configId) {
        LambdaQueryWrapperX<AppointmentConfigDept> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(AppointmentConfigDept::getConfigId, configId);
        //查询该配置是否有科室使用
        long count = count(lambdaQueryWrapperX);
        return count == 0;
    }


    @Override
    public List<AppointmentConfigDept> getConfigDept(Long deptId) {
        LambdaQueryWrapperX<AppointmentConfigDept> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(AppointmentConfigDept::getDeptId, deptId);
        return list(lambdaQueryWrapperX);
    }

    @Override
    public boolean isExist(AppointmentConfigDept apt) {
        Long deptId = apt.getDeptId();
        Integer weekId = apt.getWeekId();
        AppointmentConfig newConfigId = appointmentConfigMapper.selectById(apt.getConfigId());
        //1、根据星期和时间查询科室配置
        List<AppointmentConfigDept> appointmentConfigDept = appointmentConfigDeptMapper.getAppointmentConfigDept(deptId, weekId);
        for (AppointmentConfigDept configDept : appointmentConfigDept){
            Long deptConfigId = configDept.getConfigId();
            //1、查看根据查询条件查看上午或者下午是否有配置
            AppointmentConfig appointmentConfig = appointmentConfigMapper.selectById(deptConfigId);
            String timeClass = appointmentConfig.getTimeClass();
            if (timeClass.equals(newConfigId.getTimeClass())){
                return false;
            }
        }
        return true;
    }


}




