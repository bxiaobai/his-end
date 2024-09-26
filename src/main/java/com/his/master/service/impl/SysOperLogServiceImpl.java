package com.his.master.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.dto.log.PageLogDTO;
import com.his.master.model.entity.SysOperLog;
import com.his.master.model.vo.log.LogVO;
import com.his.master.service.SysOperLogService;
import com.his.master.mapper.SysOperLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service实现
 * @createDate 2024-06-29 10:36:43
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog>
        implements SysOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;

    @Override
    public void insertOperlog(SysOperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询操作日志列表
     *
     * @param sysOperLog
     * @return
     */
    @Override
    public Page<SysOperLog> listOperLog(PageLogDTO sysOperLog) {
        Page<SysOperLog> page = new Page<>(sysOperLog.getCurrent(), sysOperLog.getPageSize());
        return page(page, buildQueryWrapper(sysOperLog));

    }

    /**
     * 删除操作日志
     * @param id
     * @return
     */
    @Override
    public boolean deleteOperLog(Long id) {
        return removeById(id);
    }

    /**
     * 清空操作日志
     * @return
     */
    @Override
    public int cleanOperLog() {
      return baseMapper.clean();
    }

    /**
     * 根据id查询操作日志
     *
     * @param id
     * @return
     */
    @Override
    public SysOperLog getOperLogById(Long id) {
        return getById(id);
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<SysOperLog> buildQueryWrapper(PageLogDTO sysOperLog) {
        LambdaQueryWrapper<SysOperLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(sysOperLog.getOperIp() != null && !sysOperLog.getOperIp().isEmpty(), SysOperLog::getOperIp, sysOperLog.getOperIp());
        queryWrapper.eq(sysOperLog.getOperName() != null && !sysOperLog.getOperName().isEmpty(), SysOperLog::getOperName, sysOperLog.getOperName());
        queryWrapper.eq(sysOperLog.getStatus() != null && !sysOperLog.getStatus().isEmpty(), SysOperLog::getStatus, sysOperLog.getStatus());
        queryWrapper.like(sysOperLog.getTitle() != null && !sysOperLog.getTitle().isEmpty(), SysOperLog::getTitle, sysOperLog.getTitle());
        queryWrapper.eq(sysOperLog.getRequestMethod() != null && !sysOperLog.getRequestMethod().isEmpty(), SysOperLog::getRequestMethod, sysOperLog.getRequestMethod());
        return queryWrapper;
    }


}




