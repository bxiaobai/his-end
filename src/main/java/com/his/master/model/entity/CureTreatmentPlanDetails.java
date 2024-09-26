package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName cure_treatment_plan_details
 */
@TableName(value ="cure_treatment_plan_details")
@Data
public class CureTreatmentPlanDetails implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 治疗方案主表id
     */
    private Long medicalOrderId;

    /**
     * 
     */
    private Date firstUseTime;

    /**
     * 总次数
     */
    private Integer totalTimes;

    /**
     * 已使用次数
     */
    private Integer usedTimes;

    /**
     * 使用状态
     */
    private String usageStatus;

    /**
     * 
     */
    private Long patientId;

    @TableField(exist = false)
    private List<CureTreatmentPlanMedicineDetails> cureTreatmentPlanMedicineDetails;
    /**
     * 方案使用情况
     */
    @TableField(exist = false)
    private List<CureMedicalRecords> cureMedicalRecords;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}