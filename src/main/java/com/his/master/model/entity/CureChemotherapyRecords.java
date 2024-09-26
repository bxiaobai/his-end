package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName cure_chemotherapy_records
 */
@TableName(value ="cure_chemotherapy_records")
@Data
public class CureChemotherapyRecords implements Serializable {
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
     * 化疗时间
     */
    private Date chemotherapyTime;

    /**
     * 方案开局时间
     */
    private Date prescriptionTime;

    /**
     * 开方人
     */
    private String prescriber;

    /**
     * 值班医生
     */
    private String onDutyPhysician;

    /**
     * 静脉通路
     */
    private String ivAccess;

    //创建日期
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date creationDate;

    //创建人
    private String creator;

    @TableField(exist = false)
    private CureChemotherapyDailySigns cureChemotherapyDailySigns;

    @TableField(exist = false)
    private List<CureChemoDailyMedication> cureChemoDailyMedication;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}