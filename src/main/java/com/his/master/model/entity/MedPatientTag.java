package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 患者标签映射表
 * @TableName med_patient_tag
 */
@TableName(value ="med_patient_tag")
@Data
public class MedPatientTag implements Serializable {
    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 标签ID
     */
    private Long tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}