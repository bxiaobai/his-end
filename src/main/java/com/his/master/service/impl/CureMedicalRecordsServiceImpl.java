package com.his.master.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.mapper.*;
import com.his.master.model.dto.cure.PatientTreatmentDTO;
import com.his.master.model.entity.*;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureMedicalRecordsService;
import com.his.master.service.CureVitalsignsRecordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Administrator
* @description 针对表【cure_medical_records】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CureMedicalRecordsServiceImpl extends ServiceImpl<CureMedicalRecordsMapper, CureMedicalRecords>
    implements CureMedicalRecordsService{

    @Resource
    private CureMedicalRecordsMapper cureMedicalRecordsMapper;

    @Resource
    private CureMedicalDetailsMapper cureMedicalDetailsMapper;

    @Resource
    private CureDiagnosisRecordsMapper cureDiagnosisRecordsMapper;

    @Resource
    private CureChemotherapyRecordsMapper cureChemotherapyRecordsMapper;

    @Resource
    private CurePostTreatmentInfoMapper curePostTreatmentInfoMapper;

    @Resource
    private CureFollowUpInfoMapper cureFollowUpInfoMapper;

    @Resource
    private CureVitalsignsRecordsMapper cureVitalsignsRecordsMapper;

    @Override
    public  Map<String ,Object>  getByPatientIdAndTreatmentTime(PatientTreatmentDTO patientTreatmentDTO) {

        String treatmentTime = patientTreatmentDTO.getTreatmentTime();
        Long patientId = patientTreatmentDTO.getPatientId();
        Map<String ,Object> map = new HashMap<>();
        //查询主表
        CureMedicalRecords cureMedicalId = cureMedicalRecordsMapper.getByPatientIdAndTreatmentTime(patientId, treatmentTime);
        if (cureMedicalId == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,  "未找到治疗记录");
        }
        //主表id
        Long id = cureMedicalId.getId();
        //查询详情表
        CureMedicalDetails detailsId = cureMedicalDetailsMapper.getById(id);
        map.put("detailsId",detailsId);
        //查询诊断表
        CureDiagnosisRecords cureDiagnosisRecords = cureDiagnosisRecordsMapper.getCureDiagnosisRecords(id);
        map.put("cureDiagnosisRecords",cureDiagnosisRecords);
        //化疗记录主表(疗当日体征检测表\化疗单日治疗药物表)
        CureChemotherapyRecords cureChemotherapyById = cureChemotherapyRecordsMapper.getCureChemotherapyRecordsById(id);
        map.put("cureChemotherapyById",cureChemotherapyById);
        //治疗后信息表(不良反应表)
        CurePostTreatmentInfo curePostTreatmentInfoById = curePostTreatmentInfoMapper.getCurePostTreatmentInfoById(id);
        map.put("curePostTreatmentInfoById",curePostTreatmentInfoById);
        //随访信息表(随访信息详情表)
        CureFollowUpInfo cureFollowUpInfoById = cureFollowUpInfoMapper.getCureFollowUpInfoById(id);
        map.put("cureFollowUpInfoById",cureFollowUpInfoById);
        //体征记录表
        CureVitalsignsRecords cureVitalsignsRecords = cureVitalsignsRecordsMapper.getCureVitalsignsRecords(id);
        map.put("cureVitalsignsRecords",cureVitalsignsRecords);
        map.put("id",id);
        //当前查询的时间
        map.put("treatmentTime",treatmentTime);
        return map;
    }

    /**
     * 查询该患者所有治疗记录
     * @param patientId
     * @return
     */
    @Override
    public List<CureMedicalRecords> getAllTreatmentTime(Long patientId) {
        return list(new LambdaQueryWrapper<CureMedicalRecords>().eq(CureMedicalRecords::getPatientId,patientId));
    }

    /**
     * 添加治疗记录
     * @param cureMedicalRecords
     * @return
     */
    @Override
    public boolean addTreatment(CureMedicalRecords cureMedicalRecords) {
        return save(cureMedicalRecords);
    }

    /**
     * 删除治疗记录
     * @param id
     * @return
     */
    @Override
    public boolean deleteTreatment(Long id) {
        return removeById(id);
    }

    /**
     * 根据治疗方案id查询治疗记录
     * @param planId
     * @return
     */
    @Override
    public List<CureMedicalRecords> getMedicalRecordsByPlanId(Long planId) {
        return list(new LambdaQueryWrapperX<CureMedicalRecords>().eq(CureMedicalRecords::getPlanId,planId));
    }
}




