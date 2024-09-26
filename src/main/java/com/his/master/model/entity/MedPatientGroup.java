package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 患者分组映射表
 * @TableName med_patient_group
 */
@TableName(value ="med_patient_group")
@Data
public class MedPatientGroup implements Serializable {
    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 分组ID
     */
    private Long groupId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}