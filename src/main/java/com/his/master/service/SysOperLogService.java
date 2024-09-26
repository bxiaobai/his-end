package com.his.master.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.model.dto.log.PageLogDTO;
import com.his.master.model.entity.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service
* @createDate 2024-06-29 10:36:43
*/
public interface SysOperLogService extends IService<SysOperLog> {

    void insertOperlog(SysOperLog operLog);

    Page<SysOperLog> listOperLog(PageLogDTO sysOperLog);

    boolean deleteOperLog(Long id);

    int cleanOperLog();

    SysOperLog getOperLogById(Long id);
}
