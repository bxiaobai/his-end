package com.his.master.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.mapper.AppointmentRoomMapper;
import com.his.master.model.dto.cure.AppointmentConfigDTO;
import com.his.master.model.dto.cure.DeptConfigDTO;
import com.his.master.model.entity.AppointmentConfig;
import com.his.master.model.entity.AppointmentConfigDept;
import com.his.master.model.entity.AppointmentSource;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.AppointmentConfigDeptService;
import com.his.master.service.AppointmentConfigService;
import com.his.master.mapper.AppointmentConfigMapper;
import com.his.master.service.AppointmentSourceService;
import com.his.master.utils.DateUtils;
import com.his.master.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【appointment_config(号源模板主表)】的数据库操作Service实现
 * @createDate 2024-09-13 16:03:26
 */
@Service
public class AppointmentConfigServiceImpl extends ServiceImpl<AppointmentConfigMapper, AppointmentConfig>
        implements AppointmentConfigService {

    @Resource
    private AppointmentConfigDeptService appointmentConfigDeptService;

    @Resource
    private AppointmentConfigMapper appointmentConfigMapper;

    @Resource
    private AppointmentSourceService appointmentSourceService;

    @Override
    public boolean addConfig(AppointmentConfig appointmentConfig) {
        String startTime = DateUtil.format(appointmentConfig.getStartTime(), "HH:mm");
        String endTime = DateUtil.format(appointmentConfig.getEndTime(), "HH:mm");
        if (!isExist(appointmentConfig)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, startTime + "-" + endTime + "已有相同配置");
        }
        return save(appointmentConfig);
    }

    /**
     * 1、查询是否有相同配置(时间类型相同：上午下午、开始时间和结束时间都相同,不准添加)
     * 2、在科室页面分配号源配置模板，可以分配1-7用同样的
     * 2、每个科室只可以用一个配置(给号源分配科室时，将未分配科室列出来，可以点击查看已分配科室进行修改)
     * 3、如果号源模板已经生成了，则不能修改
     * 4、添加配置可以给周一到周五直接添加相同的配置
     */
    @Override
    public boolean deleteConfig(Long id) {
        boolean existDeptConfig = appointmentConfigDeptService.isExistDeptConfig(id);
        if (!existDeptConfig) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该配置已有科室使用，不允许删除");
        }
        return removeById(id);
    }

    @Override
    public boolean updateConfig(AppointmentConfig appointmentConfig) {
        Long configId = appointmentConfig.getConfigId();
        boolean existDeptConfig = appointmentConfigDeptService.isExistDeptConfig(configId);
        if (!existDeptConfig) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该配置已有科室使用，不允许修改");
        }
        return updateById(appointmentConfig);
    }

    @Override
    public AppointmentConfig getConfigById(Long id) {
        return getById(id);
    }

    @Override
    public PageResult<AppointmentConfig> pageConfig(AppointmentConfigDTO appointmentConfig) {
        return appointmentConfigMapper.pageConfig(appointmentConfig);
    }

    @Override
    @Transactional
    public boolean generateConfig(Long id, List<String> dataList) {
        //需要生成的时间
        //开始日期
        String startData = dataList.get(0);
        //结束日期
        String endData = dataList.get(1);
        List<DateTime> dateTimes = generateDatesBetween(startData, endData);
        //查询科室配置
        List<AppointmentConfigDept> configDept = appointmentConfigDeptService.getConfigDept(id);
        if (configDept.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "该科室没有进行号源配置");
        }
        for (DateTime dateTime : dateTimes){
            //如果有就查询号源开始时间和结束时间,和weekId
            //查询是星期几
            Integer week = DateUtils.getWeek(DateUtil.dayOfWeekEnum(dateTime).name());
            //根据星期判断应该使用哪个配置
            for (AppointmentConfigDept aLong : configDept) {
                if (week.equals(aLong.getWeekId())){
                    AppointmentConfig configById = getConfigById(aLong.getConfigId());
                    AppointmentSource appointmentSource = appointmentSourceService.buildSource(configById, id, dateTime);
                    //添加号源
                    appointmentSourceService.save(appointmentSource);
                }
            }
        }
        return true;
    }


    /**
     * 判断是否有相同的号源配置
     *
     * @param appointmentConfig
     * @return
     */
    @Override
    public boolean isExist(AppointmentConfig appointmentConfig) {
        //时间
        LambdaQueryWrapperX<AppointmentConfig> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX
                .eq(AppointmentConfig::getTimeClass, appointmentConfig.getTimeClass())
                .eq(AppointmentConfig::getStartTime, appointmentConfig.getStartTime());
        List<AppointmentConfig> list = list(lambdaQueryWrapperX);
        return list.isEmpty();
    }

    @Override
    @Transactional
    public boolean allocateConfig(List<DeptConfigDTO> deptConfigDTOList) {
        //组装数据
        List<AppointmentConfigDept> list = new ArrayList<>();
        List<AppointmentConfigDept> savaList = new ArrayList<>();
        for (DeptConfigDTO configId : deptConfigDTOList){
            List<Long> configIds = configId.getConfigIds();
            for (Long  ids : configIds){
                AppointmentConfigDept appointmentConfigDept = new AppointmentConfigDept();
                appointmentConfigDept.setConfigId(ids);
                appointmentConfigDept.setWeekId(configId.getWeekId());
                appointmentConfigDept.setDeptId(configId.getDeptId());
                list.add(appointmentConfigDept);
            }
        }

        for (AppointmentConfigDept aLong : list) {
            boolean exist = appointmentConfigDeptService.isExist(aLong);
            if (exist) {
                savaList.add(aLong);
            }
        }
        appointmentConfigDeptService.saveBatch(savaList);
        return true;
    }


    /**
     * 根据开始时间和结束时间获取日期列表
     */
    private List<DateTime> generateDatesBetween(String startDate, String endDate) {
        DateTime s = DateUtil.parse(startDate);
        DateTime e = DateUtil.parse(endDate);
        return DateUtil.rangeToList(s, e, DateField.DAY_OF_YEAR);
    }

}




