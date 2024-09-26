package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName cure_chemotherapy_daily_signs
 */
@TableName(value ="cure_chemotherapy_daily_signs")
@Data
public class CureChemotherapyDailySigns implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 化疗记录主表id
     */
    private Long chemotherapyRecordId;

    /**
     * 体温
     */
    private Double temperature;

    /**
     * 血压
     */
    private String bloodPressure;

    /**
     * 血氧
     */
    private Double oxygenSaturation;

    /**
     * 血糖
     */
    private Double bloodSugar;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 身高
     */
    private Double height;

    /**
     * BMI
     */
    private Double bmi;

    /**
     * 检查结果
     */
    private String examinationResult;

    /**
     * 治疗开始时间
     */
    private Date treatmentStartTime;

    /**
     * 治疗结束时间
     */
    private Date treatmentEndTime;

    private Long assessmentResult;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}