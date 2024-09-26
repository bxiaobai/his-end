package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.cure.CureTreatmentRecordsDTO;
import com.his.master.model.entity.CureMedicalRecords;
import com.his.master.model.entity.CureTreatmentPlanDetails;
import com.his.master.model.entity.CureTreatmentPlanMain;
import com.his.master.model.entity.CureTreatmentRecords;
import com.his.master.service.*;
import com.his.master.mapper.CureTreatmentRecordsMapper;
import com.his.master.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Administrator
* @description 针对表【cure_treatment_records】的数据库操作Service实现
* @createDate 2024-09-11 18:38:09
*/
@Service
public class CureTreatmentRecordsServiceImpl extends ServiceImpl<CureTreatmentRecordsMapper, CureTreatmentRecords>
    implements CureTreatmentRecordsService{

    @Resource
    private CureTreatmentRecordsMapper cureTreatmentRecordsMapper;

    @Resource
    private CureTreatmentPlanMainService cureTreatmentPlanMainService;

    @Resource
    private CureTreatmentPlanDetailsService cureTreatmentPlanDetailsService;

    @Resource
    private CureMedicalRecordsService cureMedicalRecordsService;

    @Override
    public PageResult<CureTreatmentRecords> getHistoryReport(CureTreatmentRecordsDTO cureTreatmentRecords) {
        return cureTreatmentRecordsMapper.listTreatmentRecords(cureTreatmentRecords);
    }

    @Override
    public boolean addHistoryReport(CureTreatmentRecords cureTreatmentRecords) {
        return save(cureTreatmentRecords);
    }

    @Override
    public boolean deleteHistoryReport(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateHistoryReport(CureTreatmentRecords cureTreatmentRecords) {
        return updateById(cureTreatmentRecords);
    }

    @Override
    public CureTreatmentRecords getHistoryReportDetails(Long id) {
        //根据id获取历史报告
        CureTreatmentRecords byId = getById(id);
        if (byId == null ){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR, "未找到该历史报告");
        }
        Long medicalOrderId = byId.getMedicalOrderId();
        //根据医嘱id获取医嘱信息
        CureTreatmentPlanMain treatmentPlanMain = cureTreatmentPlanMainService.getTreatmentPlanByMedicalOrderId(medicalOrderId);
        byId.setCureTreatmentPlanMain(treatmentPlanMain);
        //查询方案使用情况
        //放入医嘱列表
        return checkParams(byId);
    }
    private CureTreatmentRecords checkParams(CureTreatmentRecords cureTreatmentRecords) {
        CureTreatmentPlanMain cureTreatmentPlanMain = cureTreatmentRecords.getCureTreatmentPlanMain();
        List<CureTreatmentPlanDetails> cureTreatmentPlanDetails = cureTreatmentPlanMain.getCureTreatmentPlanDetails();
        for (CureTreatmentPlanDetails cureTreatmentPlanDetail : cureTreatmentPlanDetails){
            Long planId = cureTreatmentPlanDetail.getId();
            if (planId != null){
                List<CureMedicalRecords> medicalRecordsByPlanId = cureMedicalRecordsService.getMedicalRecordsByPlanId(planId);
                cureTreatmentPlanDetail.setCureMedicalRecords(medicalRecordsByPlanId);
            }
        }
        cureTreatmentPlanMain.setCureTreatmentPlanDetails(cureTreatmentPlanDetails);
        return cureTreatmentRecords;
    }
}




