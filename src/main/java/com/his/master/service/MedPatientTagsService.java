package com.his.master.service;

import com.his.master.model.dto.patient.AddOrUpdateTagDTO;
import com.his.master.model.entity.MedPatientTags;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.utils.PageResult;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【med_patient_tags(患者标签表)】的数据库操作Service
 * @createDate 2024-07-24 14:35:11
 */
public interface MedPatientTagsService extends IService<MedPatientTags> {

    /**
     * 给患者添加标签
     * @param addOrUpdateTagDTO
     * @return
     */
    void addOrUpdatePatientTags(AddOrUpdateTagDTO addOrUpdateTagDTO);


    /**
     * 单独删除患者标签
     * @param
     * @return
     */
    void deletePatientTags(Long id,Long patientId);

    /**
     * 判断标签是否存在
     *
     * @param tagName
     * @return
     */
    MedPatientTags isExistPatientTagName(String tagName);

    /**
     * 单独添加标签
     */
    Long addTags(MedPatientTags medPatientTags);

    /**
     * 根据患者查询未使用的标签
     */
    List<MedPatientTags> getUnusedTags(Long patientId);


    /**
     * 分页获取标签
     */
    PageResult<MedPatientTags> listPatientTags(MedPatientTags addOrUpdateTagDTO);

    /**
     * 获取患者已有标签
     */
    List<MedPatientTags> listPatientTags(Long patientId);
}
