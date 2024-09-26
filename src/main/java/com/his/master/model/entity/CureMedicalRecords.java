package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName cure_medical_records
 */
@TableName(value ="cure_medical_records")
@Data
public class CureMedicalRecords implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 医嘱id
     */
    private Long adviceId;

    /**
     * 治疗时间
     */
    private Date treatmentTime;

    /**
     * 治疗类型
     */
    private String treatmentType;

    /**
     * 患者id
     */
    private Long patientId;

    /**
     * 创建时间
     */
    private Date creationTime;

    /**
     * 治疗状态
     */
    private String treatmentStatus;

    private Long planId;

    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}