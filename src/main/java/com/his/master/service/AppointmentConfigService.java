package com.his.master.service;

import com.his.master.model.dto.cure.AppointmentConfigDTO;
import com.his.master.model.dto.cure.DeptConfigDTO;
import com.his.master.model.entity.AppointmentConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.model.entity.AppointmentConfigDept;
import com.his.master.utils.PageResult;

import java.util.List;

/**
* @author Administrator
* @description 针对表【appointment_config(号源模板主表)】的数据库操作Service
* @createDate 2024-09-13 16:03:26
*/
public interface AppointmentConfigService extends IService<AppointmentConfig> {

    /**
     * 添加
     */
    boolean addConfig(AppointmentConfig appointmentConfig);

    /**
     * 删除
     */
    boolean deleteConfig(Long id);

    /**
     * 修改
     */
    boolean updateConfig(AppointmentConfig appointmentConfig);

    /**
     * 查询详情
      */
    AppointmentConfig getConfigById(Long id);

    /**
     * 分页查询配置列表
     */
    PageResult<AppointmentConfig> pageConfig(AppointmentConfigDTO appointmentConfig);

    /**
     * 生成号源接口
     */
    boolean generateConfig(Long id ,List<String> list);


    /**
     * 判断是否有相同的号源配置
     */
    boolean isExist(AppointmentConfig appointmentConfig);

    /**
     * 给科室分配配置
     */
    boolean allocateConfig(List<DeptConfigDTO> deptConfigDTOList  );

}
