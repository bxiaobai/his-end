package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 调查问卷问题主表
 * @TableName survey_question_info
 */
@TableName(value ="survey_question_info")
@Data
public class SurveyQuestionInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关联调查问卷主表ID
     */
    private Integer surveyId;

    /**
     * 1 单选 2滑块 3只显示标题
     */
    private String questionType;

    /**
     * 问题主题
     */
    private String questionName;

    /**
     * 问题分数
     */
    private Integer questionScore;

    /**
     * 选项结果
     */
    private String result;


    @TableField(exist = false)
    private List<SurveyQuestionInfo> surveyQuestionInfoList;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}