package com.his.master.service.impl;

import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.mapper.AppointmentDetailsMapper;
import com.his.master.model.dto.cure.DeductionSourceDTO;
import com.his.master.model.entity.*;
import com.his.master.model.vo.cure.SourceTImeVO;
import com.his.master.model.vo.cure.SourceVO;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.*;
import com.his.master.mapper.AppointmentSourceMapper;
import com.his.master.utils.DateUtils;
import com.his.master.utils.PageResult;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【appointment_source(号源表)】的数据库操作Service实现
 * @createDate 2024-09-13 16:03:26
 */
@Service
public class AppointmentSourceServiceImpl extends ServiceImpl<AppointmentSourceMapper, AppointmentSource>
        implements AppointmentSourceService {

    @Resource
    private AppointmentSourceMapper appointmentSourceMapper;

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private AppointmentDetailsMapper appointmentDetailsMapper;

    @Resource
    private SysRoomDeptService sysRoomDeptService;

    @Resource
    private AppointmentRoomService appointmentRoomService;

    @Override
    @Transactional
    public synchronized boolean deductionSource(DeductionSourceDTO dto) {
        List<Long> sourceId = dto.getSourceId();
        Integer sourceType = dto.getSourceType();
        //查询需要扣减的数据是否充足
        for (Long id : sourceId) {
            Integer sourceCount = getSourceCount(id, sourceType);
            if (sourceCount == 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "号源不足,请刷新重试");
            }
            AppointmentSource appointmentSource = new AppointmentSource();
            appointmentSource.setSourceId(id);
            if (sourceType == 0) {
                appointmentSource.setRemainingCount(sourceCount - 1);
            } else {
                appointmentSource.setStandbyNumber(sourceCount - 1);
            }
            updateById(appointmentSource);
        }
        return true;
    }

    @Override
    public boolean addSource(DeductionSourceDTO dto) {
        List<Long> sourceId = dto.getSourceId();
        Integer sourceType = dto.getSourceType();
        //查询需要扣减的数据是否充足
        for (Long id : sourceId) {
            Integer sourceCount = getSourceCount(id, sourceType);
            AppointmentSource appointmentSource = new AppointmentSource();
            appointmentSource.setSourceId(id);
            if (sourceType == 0) {
                appointmentSource.setRemainingCount(sourceCount + 1);
            } else {
                appointmentSource.setStandbyNumber(sourceCount + 1);
            }
            updateById(appointmentSource);
        }
        return true;
    }

    @Override
    public List<SourceVO> pageSource(DeductionSourceDTO appointmentSource) {
        Long roomId = appointmentSource.getRoomId();
        String date = appointmentSource.getDate();
        //获取传入日期后面十天的日期
        List<Date> dateList = getDateList(date);
        //获取号源列表集合
        List<AppointmentSource> sourceList = appointmentSourceMapper.getSourceList(roomId, dateList);
        //根据条件获取预约列表
        List<AppointmentDetails> appointmentCount = appointmentDetailsMapper.getAppointmentCount(appointmentSource);
        //如果当天输液室这个座位无法选择，不可选时间放进去
        List<SourceVO> sourceVOList = mergeGroup(sourceList);
        if (!appointmentCount.isEmpty()) {
            //获取已经预约的座位号
            for (AppointmentDetails appointmentDetails : appointmentCount) {
                List<Integer> list = new ArrayList<>();
                sourceVOList.forEach(appointmentRoom -> {
                    if (appointmentRoom.getDate().equals(appointmentDetails.getAppointDate())) {
                        String appointTime = appointmentDetails.getAppointTime();
                        String[] split = appointTime.split(",");
                        list.addAll(Arrays.stream(split).map(String::trim).map(Integer::parseInt).collect(Collectors.toList()));
                        appointmentRoom.setUnTimes(list);
                    }
                });
            }
        }
        return sourceVOList.stream()
                .sorted((e1, e2) -> DateUtil.compare(e1.getDate(), e2.getDate()))
                .collect(Collectors.toList());
    }

    /**
     * 查询号源剩余数量
     *
     * @param sourceId
     * @param sourceType
     * @return
     */
    @Override
    public Integer getSourceCount(Long sourceId, Integer sourceType) {
        if (sourceType == 0) {
            return appointmentSourceMapper.getRemainingCount(sourceId);
        } else {
            return appointmentSourceMapper.getSourceCount(sourceId);
        }
    }

    @Override
    public List<AppointmentRoom> getSeatList(DeductionSourceDTO appointmentSource) {
        //根据dept获取科室信息
        Long deptId = SecurityUtils.getDeptId();
        SysDept byId = sysDeptService.getById(deptId);
        //如果是普通科室查询可用输液室
        List<AppointmentRoom> appointmentRoomList = new ArrayList<>();
        if (byId.getDeptSign() == 0) {
            List<SysRoomDept> list = sysRoomDeptService.list(new LambdaQueryWrapperX<SysRoomDept>().eq(SysRoomDept::getDeptId, deptId));
            //获取输液室列表
            for (SysRoomDept sysRoomDept : list) {
                AppointmentRoom roomById = appointmentRoomService.getRoomById(sysRoomDept.getRoomId());
                appointmentRoomList.add(roomById);
            }
        } else {
            AppointmentRoom roomById = appointmentRoomService.getRoomById(deptId);
            appointmentRoomList.add(roomById);
        }

        //根据条件获取预约列表
        List<AppointmentDetails> appointmentCount = appointmentDetailsMapper.getAppointmentCount(appointmentSource);
        //如果当天输液室有预的列表，把座位放进去
        if (!appointmentCount.isEmpty()) {
            //获取已经预约的座位号
            for (AppointmentDetails appointmentDetails : appointmentCount) {
                appointmentRoomList.forEach(appointmentRoom -> {
                    List<Long> longs = new ArrayList<>();
                    if (appointmentRoom.getTransfusionId().equals(appointmentDetails.getInfusionId())) {
                        longs.add(appointmentDetails.getSeatId());
                    }
                    appointmentRoom.setUnSeats(longs);
                });
            }
        }
        return appointmentRoomList;
    }


    /**
     * 根据传入日期获取后面十天的日期
     */
    private List<Date> getDateList(String stringTime) {
        // 获取当前日期
        Date today = DateUtil.parse(stringTime, "yyyy-MM-dd");

        // 创建一个日期范围，从今天开始，持续10天
        List<Date> dates = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            dates.add(DateUtil.offsetDay(today, i));
        }
        return dates;
    }

    /**
     * 根据日期合并分组
     */
    private List<SourceVO> mergeGroup(List<AppointmentSource> sourceList) {
        Map<Date, List<AppointmentSource>> map = sourceList.stream().collect(Collectors.groupingBy(AppointmentSource::getConfigDate));
        List<SourceVO> sourceVOList = new ArrayList<>();
        map.forEach((date, list) -> {
            SourceVO sourceVO = new SourceVO();
            List<SourceTImeVO> sourceTImeVOS = new ArrayList<>();
            for (AppointmentSource appointmentSource : list) {
                SourceTImeVO sourceTImeVO = new SourceTImeVO();
                //开始时间
                Date startTime = appointmentSource.getStartTime();
                //结束时间
                Date endTime = appointmentSource.getEndTime();
                List<Integer> integers = splitList(startTime, endTime);
                sourceTImeVO.setTimes(integers);
                sourceTImeVO.setSourceId(appointmentSource.getSourceId());
                sourceTImeVOS.add(sourceTImeVO);
                sourceTImeVO.setRemainingCount(appointmentSource.getRemainingCount());
                sourceTImeVO.setStandbyNumber(appointmentSource.getStandbyNumber());
            }
            sourceVO.setDate(date);
            sourceVO.setTimes(sourceTImeVOS);
            sourceVO.setWeek(DateUtils.getWeek(DateUtil.dayOfWeekEnum(date).name()));
            sourceVOList.add(sourceVO);
        });
        return sourceVOList;
    }

    /**
     * 根据时间拆分集合
     */
    private List<Integer> splitList(Date startTime, Date endTime) {
        //将开始时间和结束时间转换为字符串
        String startTimeStr = DateUtil.format(startTime, "HH");
        String endTimeStr = DateUtil.format(endTime, "HH");
        //将字符串转换为数字
        int i = Integer.parseInt(startTimeStr);
        int j = Integer.parseInt(endTimeStr);

        List<Integer> list = new ArrayList<>();
        // 循环遍历每个小时
        for (int hour = i; hour <= j; hour++) {
            // 格式化小时数为两位数字符串
            list.add(hour);
        }
        return list;
    }

    /**
     * 构建号源列表
     */
    @Override
    public AppointmentSource buildSource(AppointmentConfig appointmentConfig, Long deptId, Date date) {
        AppointmentSource appointmentSource = new AppointmentSource();
        //号源总数
        appointmentSource.setSourceTotal(appointmentConfig.getSourceNumber());
        appointmentSource.setRemainingCount(appointmentConfig.getSourceNumber());
        appointmentSource.setStartTime(appointmentConfig.getStartTime());
        appointmentSource.setEndTime(appointmentConfig.getEndTime());
        //输液室id
        appointmentSource.setTransfusionId(deptId);
        appointmentSource.setStandbyNumber(appointmentConfig.getStandbyNumber());
        appointmentSource.setTimeClass(appointmentConfig.getTimeClass());
        appointmentSource.setConfigDate(date);
        return appointmentSource;
    }


}



