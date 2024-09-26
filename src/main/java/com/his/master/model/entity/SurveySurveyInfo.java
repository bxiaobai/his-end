package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 调查问卷主表
 * @TableName survey_survey_info
 */
@TableName(value ="survey_survey_info")
@Data
public class SurveySurveyInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主题
     */
    private String surveyName;

    /**
     * 描述
     */
    private String surveyDescription;

    /**
     * 排序
     */
    private Integer surveySort;

    @TableField(exist = false)
    private List<SurveyQuestionInfo> questionInfoList;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}