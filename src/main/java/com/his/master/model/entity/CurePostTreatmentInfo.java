package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName cure_post_treatment_info
 */
@TableName(value ="cure_post_treatment_info")
@Data
public class CurePostTreatmentInfo implements Serializable {
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
     * 结束时间
     */
    private Date endTime;

    /**
     * 化疗后评估
     */
    private String chemotherapyAssessment;

    /**
     * 治疗所用时长
     */
    private Date treatmentDuration;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date creationDate;

    /**
     * 创建人
     */
    private String creator;

    @TableField(exist = false)
    private List<CureAdverseReactions> cureAdverseReactions;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}