package com.his.master.mapper;

import com.his.master.model.dto.survey.SurveyQueryDTO;
import com.his.master.model.entity.SurveyOptionInfo;
import com.his.master.model.entity.SurveyQuestionInfo;
import com.his.master.model.entity.SurveySurveyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.his.master.mybatis.BaseMapperX;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.mybatis.MPJLambdaWrapperX;
import com.his.master.mybatis.PageParam;
import com.his.master.utils.PageResult;

/**
 * @author Administrator
 * @description 针对表【survey_survey_info(调查问卷主表)】的数据库操作Mapper
 * @createDate 2024-08-10 17:32:18
 * @Entity com.his.master.model.entity.SurveySurveyInfo
 */
public interface SurveySurveyInfoMapper extends BaseMapperX<SurveySurveyInfo> {

    default PageResult<SurveySurveyInfo> findSurveyPage(SurveyQueryDTO surveyQueryDTO) {
        MPJLambdaWrapperX<SurveySurveyInfo> wrapper = new MPJLambdaWrapperX<>();
        wrapper.selectAll(SurveySurveyInfo.class)
                .leftJoin(SurveyQuestionInfo.class, SurveyQuestionInfo::getSurveyId, SurveySurveyInfo::getId)
                .leftJoin(SurveyOptionInfo.class, SurveyOptionInfo::getSurveyId, SurveySurveyInfo::getId)
                .likeIfExists(SurveySurveyInfo::getSurveyName, surveyQueryDTO.getSurveyName());
        return selectJoinPage(surveyQueryDTO, SurveySurveyInfo.class, wrapper);
    }
}




