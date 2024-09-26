package com.his.master.service;

import com.his.master.model.entity.AppointmentConfigDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_config_dept(号源配置科室表)】的数据库操作Service
* @createDate 2024-09-13 16:03:26
*/
public interface AppointmentConfigDeptService extends IService<AppointmentConfigDept> {

    /**
     * 根据配置id查询是否有科室在使用
     */
    boolean isExistDeptConfig(Long configId);


    /**
     * 查询该科室是否已经分配配置(根据科室id、根据当前配置周一-周五、)
     */
    boolean isExist(AppointmentConfigDept aLong);

    /**
     * 根据科室id查询所有配置
     */
    List<AppointmentConfigDept>  getConfigDept(Long deptId);

}
