package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName cure_diagnosis_records
 */
@TableName(value ="cure_diagnosis_records")
@Data
public class CureDiagnosisRecords implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 医疗记录表id
     */
    private Long medicalRecordId;

    /**
     * 初步诊断
     */
    private String preliminaryDiagnosis;

    /**
     * 医嘱
     */
    private String prescription;

    /**
     * 开方时间
     */
    private Date prescriptionTime;

    /**
     * 随访计划
     */
    private String followUpPlan;

    /**
     * 处方记录
     */
    private String prescriptionRecord;

    /**
     * 治疗计划
     */
    private String treatmentPlan;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date creationDate;

    /**
     * 
     */
    private String creator;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}