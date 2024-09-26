package com.his.master.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.his.master.model.dto.med.MedPatientsQueryDTO;
import com.his.master.model.dto.patient.MedPatientsAddDTO;
import com.his.master.model.entity.MedPatients;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.model.vo.patient.MedPatientsVO;
import com.his.master.utils.PageResult;

import java.util.Map;

/**
* @author Administrator
* @description 针对表【med_patients(患者基本信息表)】的数据库操作Service
* @createDate 2024-07-01 18:46:56
*/
public interface MedPatientsService extends IService<MedPatients> {

    /**
     * 分页获取患者列表
     */
    PageResult<MedPatientsVO> listPatients(MedPatientsQueryDTO medPatients);

    /**
     * 获取患者详情
     */
    Map<String, Object> getPatientsById(Long id);

    /**
     * 添加患者
     */
    boolean addPatients(MedPatientsAddDTO medPatients);


    /**
     * 更新患者
     */
    boolean updatePatients(MedPatients medPatients);

    /**
     * 删除患者
     */
    boolean deletePatients(Long id);

    /**
     * 构建查询条件
     */

    /**
     * 是否已经存在患者
     */
    boolean isExistPatients(MedPatients medPatients);

    /**
     * 根据卡号查询患者信息
     */
    MedPatients getPatientsByCardNo(String cardNo);
}
