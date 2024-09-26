package com.his.master.controller.survey;

import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.dto.survey.SurveyQueryDTO;
import com.his.master.model.entity.MedGroups;
import com.his.master.model.entity.SurveySurveyInfo;
import com.his.master.model.vo.survey.SurveyPageVO;
import com.his.master.service.SurveySurveyInfoService;
import com.his.master.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/survey")
@Api("问卷服务")
public class SurveyController {

    @Resource
    private SurveySurveyInfoService surveySurveyInfoService;

    /**
     * 添加问卷
     *
     * @param surveySurveyInfoDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加问卷")
    public boolean addSurvey(@RequestBody SurveySurveyInfo surveySurveyInfoDTO) {
        return surveySurveyInfoService.addSurvey(surveySurveyInfoDTO);
    }

    /**
     * 修改问卷
     *
     * @param surveySurveyInfoDTO
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改问卷")
    public boolean updateSurvey(@RequestBody SurveySurveyInfo surveySurveyInfoDTO) {
        return surveySurveyInfoService.updateSurvey(surveySurveyInfoDTO);
    }

    /**
     * 删除问卷
     *
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除问卷")
    public boolean deleteSurvey(@PathVariable Long id) {
        return surveySurveyInfoService.deleteSurvey(id);
    }

    /**
     * 查询问卷
     *
     * @param id
     * @return
     */
    @PostMapping("/get/{id}")
    @ApiOperation(value = "查询问卷")
    public SurveySurveyInfo getSurvey(@PathVariable Long id) {
        return surveySurveyInfoService.getSurvey(id);
    }

    /**
     * 查询问卷列表
     *
     * @param surveySurveyInfoDTO
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "查询问卷列表")
    public BaseResponse<PageResult<SurveySurveyInfo>> pageSurvey(@RequestBody SurveyQueryDTO surveySurveyInfoDTO) {
        return ResultUtils.success(surveySurveyInfoService.pageSurvey(surveySurveyInfoDTO));
    }



}
