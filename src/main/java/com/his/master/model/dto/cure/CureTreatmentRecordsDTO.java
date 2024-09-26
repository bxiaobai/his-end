package com.his.master.model.dto.cure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.his.master.model.entity.CureTreatmentPlanMain;
import com.his.master.mybatis.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName cure_treatment_records
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="cure_treatment_records")
@Data
public class CureTreatmentRecordsDTO extends PageParam implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 治疗类型
     */
    private String treatmentType;

    /**
     * 医嘱id
     */
    private Long medicalOrderId;

    /**
     * 治疗状态
     */
    private String treatmentStatus;

    /**
     * 治疗结果
     */
    private String treatmentResult;

    /**
     * 创建日期
     */
    private List<String> creationDate;
    /**
     * 患者id
     */
    private Long patientId;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}