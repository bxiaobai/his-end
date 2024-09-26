package com.his.master.service;

import com.his.master.model.dto.cure.CureTreatmentRecordsDTO;
import com.his.master.model.entity.CureTreatmentRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.utils.PageResult;

import java.util.List;

/**
* @author Administrator
* @description 针对表【cure_treatment_records】的数据库操作Service
* @createDate 2024-09-11 18:38:09
*/
public interface CureTreatmentRecordsService extends IService<CureTreatmentRecords> {

    /**
     * 分页查询历史报告
     */
    PageResult<CureTreatmentRecords> getHistoryReport(CureTreatmentRecordsDTO cureTreatmentRecords);

    /**
     * 添加历史报告记录
     */
    boolean addHistoryReport(CureTreatmentRecords cureTreatmentRecords);

    /**
     * 删除历史报告记录
     */
    boolean deleteHistoryReport(Long id);

    /**
     * 更新历史报告记录
     */
    boolean updateHistoryReport(CureTreatmentRecords cureTreatmentRecords);

    /**
     * 获取历史报告详情
     */
    CureTreatmentRecords getHistoryReportDetails(Long id);
}
