package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.dto.survey.SurveyQueryDTO;
import com.his.master.model.entity.SurveySurveyInfo;
import com.his.master.service.SurveySurveyInfoService;
import com.his.master.mapper.SurveySurveyInfoMapper;
import com.his.master.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【survey_survey_info(调查问卷主表)】的数据库操作Service实现
* @createDate 2024-08-10 17:32:18
*/
@Service
public class SurveySurveyInfoServiceImpl extends ServiceImpl<SurveySurveyInfoMapper, SurveySurveyInfo>
    implements SurveySurveyInfoService{

    @Resource
    private SurveySurveyInfoMapper surveySurveyInfoMapper;

    @Override
    public boolean addSurvey(SurveySurveyInfo surveySurveyInfoDTO) {
        return false;
    }

    @Override
    public boolean deleteSurvey(Long id) {
        return false;
    }

    @Override
    public SurveySurveyInfo getSurvey(Long id) {
        return null;
    }

    @Override
    public PageResult<SurveySurveyInfo> pageSurvey(SurveyQueryDTO surveySurveyInfoDTO) {
        return surveySurveyInfoMapper.findSurveyPage(surveySurveyInfoDTO);
    }

    @Override
    public boolean updateSurvey(SurveySurveyInfo surveySurveyInfoDTO) {
        return false;
    }
}




