package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 调查问卷问题选项主表
 * @TableName survey_option_info
 */
@TableName(value ="survey_option_info")
@Data
public class SurveyOptionInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 调查问卷ID
     */
    private Integer surveyId;

    /**
     * 问题ID
     */
    private Integer questionId;

    /**
     * 选项名称
     */
    private String optionName;

    /**
     * 选项等级
     */
    private Integer level;

    /**
     * 选项结果
     */
    private String result;

    /**
     * 选项分数
     */
    private Integer optionScore;

    /**
     * 滑块最大值
     */
    private Integer optionMax;

    /**
     * 滑块最小值
     */
    private Integer optionMin;

    /**
     * 滑块最大值名称
     */
    private String optionMaxName;

    /**
     * 滑块最小值名称
     */
    private String optionMinName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}