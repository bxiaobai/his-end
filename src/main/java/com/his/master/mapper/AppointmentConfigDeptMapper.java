package com.his.master.mapper;

import com.his.master.model.entity.AppointmentConfigDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.LambdaQueryWrapperX;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_config_dept(号源配置科室表)】的数据库操作Mapper
* @createDate 2024-09-13 16:03:26
* @Entity com.his.master.model.entity.AppointmentConfigDept
*/
public interface AppointmentConfigDeptMapper extends BaseMapper<AppointmentConfigDept> {

    default List<AppointmentConfigDept> getAppointmentConfigDept(Long deptId, Integer weekId){
        LambdaQueryWrapperX<AppointmentConfigDept> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.eq(AppointmentConfigDept::getWeekId, weekId);
        lambdaQueryWrapperX.eq(AppointmentConfigDept::getDeptId, deptId);
        return selectList(lambdaQueryWrapperX);
    }
}




