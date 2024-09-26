package com.his.master.mapper;

import com.his.master.model.entity.SysOperLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
* @author Administrator
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Mapper
* @createDate 2024-06-29 10:36:43
* @Entity com.his.master.model.entity.SysOperLog
*/
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    void insertOperlog(SysOperLog operLog);

    @Update("TRUNCATE TABLE 'sys_oper_log'")
    int clean();
}




