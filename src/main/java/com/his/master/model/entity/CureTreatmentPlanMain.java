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
 * @TableName cure_treatment_plan_main
 */
@TableName(value ="cure_treatment_plan_main")
@Data
public class CureTreatmentPlanMain implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 医嘱id
     */
    private Long adviceId;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 
     */
    private Date expectedEndDate;

    /**
     * 治疗周期
     */
    private String treatmentCycle;

    /**
     * 治疗频率
     */
    private String treatmentFrequency;

    /**
     *  prescribing_doctor
     */
    private String prescribingDoctor;

    @TableField(exist = false)
    private List<CureTreatmentPlanDetails> cureTreatmentPlanDetails;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}