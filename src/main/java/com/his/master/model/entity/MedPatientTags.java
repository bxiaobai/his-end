package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 患者标签表
 * @TableName med_patient_tags
 */
@TableName(value ="med_patient_tags")
@Data
public class MedPatientTags implements Serializable {
    /**
     * 标签ID
     */
    @TableId(type = IdType.AUTO)
    private Long tagId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}