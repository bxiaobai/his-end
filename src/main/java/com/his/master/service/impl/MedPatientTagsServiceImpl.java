package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.mapper.MedPatientTagMapper;
import com.his.master.model.dto.patient.AddOrUpdateTagDTO;
import com.his.master.model.entity.MedPatientTag;
import com.his.master.model.entity.MedPatientTags;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.MedPatientTagsService;
import com.his.master.mapper.MedPatientTagsMapper;
import com.his.master.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【med_patient_tags(患者标签表)】的数据库操作Service实现
 * @createDate 2024-07-24 14:35:11
 */
@Service
public class MedPatientTagsServiceImpl extends ServiceImpl<MedPatientTagsMapper, MedPatientTags>
        implements MedPatientTagsService {


    @Resource
    private MedPatientTagMapper medPatientTagMapper;

    @Resource
    private MedPatientTagsMapper medPatientTagsMapper;


    @Override
    public void addOrUpdatePatientTags(AddOrUpdateTagDTO addOrUpdateTagDTO) {
        // 传递过来tag集合，患者id
        String tagName = addOrUpdateTagDTO.getTagName();
        Long patientId = addOrUpdateTagDTO.getPatientId();
        //查询标签id
        MedPatientTags existPatientTagName = isExistPatientTagName(tagName);
        if (existPatientTagName == null) {
            MedPatientTags medPatientTags = new MedPatientTags();
            medPatientTags.setTagName(tagName);
            Long l = addTags(medPatientTags);
            addOrUpdateTagDTO.setTagId(l);
        } else {
            addOrUpdateTagDTO.setTagId(existPatientTagName.getTagId());
        }

        //判断患者是否已有该标签
        boolean existPatientTag = medPatientTagMapper.isExistPatientTag(patientId, addOrUpdateTagDTO.getTagId());
        if (!existPatientTag){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "患者已有该标签");
        }
        medPatientTagMapper.insertTags(patientId, addOrUpdateTagDTO.getTagId());
    }

    /**
     * 删除患者关联标签
     *
     * @param id
     * @return
     */
    @Override
    public void deletePatientTags(Long id, Long patientId) {
        medPatientTagMapper.deleteTagsAndPatient(patientId, id);
    }


    @Override
    public MedPatientTags isExistPatientTagName(String tagName) {
        return getOne(new LambdaQueryWrapperX<MedPatientTags>().eq(MedPatientTags::getTagName, tagName));
    }


    @Override
    public Long addTags(MedPatientTags medPatientTags) {
        String tagName = medPatientTags.getTagName();
        if (isExistPatientTagName(tagName) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签已存在");
        }
        save(medPatientTags);
        return medPatientTags.getTagId();
    }

    @Override
    public List<MedPatientTags> getUnusedTags(Long patientId) {
        // 获取患者已有标签
        List<Long> tagsUnusedTags = medPatientTagMapper.getPatientTags(patientId).stream().map(MedPatientTag::getTagId).collect(Collectors.toList());
        // 获取时排除已有标签
        return medPatientTagsMapper.getUnusedTags(tagsUnusedTags);
    }

    @Override
    public PageResult<MedPatientTags> listPatientTags(MedPatientTags addOrUpdateTagDTO) {
        return null;
    }

    /**
     * 获取患者已有标签
     *
     * @param patientId
     * @return
     */
    @Override
    public List<MedPatientTags> listPatientTags(Long patientId) {
        return medPatientTagsMapper.getPatientTags(patientId);
    }

}




