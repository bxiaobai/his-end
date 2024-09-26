package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.MedPatientTag;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.MedPatientTagService;
import com.his.master.mapper.MedPatientTagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【med_patient_tag(患者标签映射表)】的数据库操作Service实现
* @createDate 2024-07-24 14:35:11
*/
@Service
public class MedPatientTagServiceImpl extends ServiceImpl<MedPatientTagMapper, MedPatientTag>
    implements MedPatientTagService{

    @Override
    public List<MedPatientTag> getUsedTags(Long patientId) {
        return list(new LambdaQueryWrapperX<MedPatientTag>().eq(MedPatientTag::getPatientId, patientId));
    }
}




