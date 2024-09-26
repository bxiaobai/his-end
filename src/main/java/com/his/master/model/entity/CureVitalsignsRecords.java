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
 * @TableName cure_vitalsigns_records
 */
@TableName(value ="cure_vitalsigns_records")
@Data
public class CureVitalsignsRecords implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 医疗记录id
     */
    private Long medicalRecordId;

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
     * 辅助检查内容
     */
    private String auxiliaryExamination;

    /**
     * 创建日期
     */
    private Date creationDate;

    /**
     * 创建人
     */
    private String creator;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}