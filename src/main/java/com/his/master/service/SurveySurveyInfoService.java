package com.his.master.service;

import com.his.master.model.dto.survey.SurveyQueryDTO;
import com.his.master.model.entity.SurveySurveyInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.utils.PageResult;

/**
* @author Administrator
* @description 针对表【survey_survey_info(调查问卷主表)】的数据库操作Service
* @createDate 2024-08-10 17:32:18
*/
public interface SurveySurveyInfoService extends IService<SurveySurveyInfo> {

    /**
     * 添加问卷
     * @param surveySurveyInfoDTO
     * @return
     */
    boolean addSurvey(SurveySurveyInfo surveySurveyInfoDTO);

    /**
     * 删除问卷
     * @param id
     * @return
     */
    boolean deleteSurvey(Long id);

    /**
     * 查询问卷
     * @param id
     * @return
     */
    SurveySurveyInfo getSurvey(Long id);

    /**
     * 查询问卷列表
     * @param surveySurveyInfoDTO
     * @return
     */
    PageResult<SurveySurveyInfo> pageSurvey(SurveyQueryDTO surveySurveyInfoDTO);


    /**
     * 修改问卷
     * @param surveySurveyInfoDTO
     * @return
     */
    boolean updateSurvey(SurveySurveyInfo surveySurveyInfoDTO);
}
