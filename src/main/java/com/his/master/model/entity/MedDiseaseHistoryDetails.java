package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 疾病史详情表
 * @TableName med_disease_history_details
 */
@TableName(value ="med_disease_history_details")
@Data
public class MedDiseaseHistoryDetails implements Serializable {
    /**
     * 疾病史详情ID
     */
    @TableId(type = IdType.AUTO)
    private Long detailId;

    /**
     * 关联的既往史主表ID
     */
    private Long historyId;

    /**
     * 疾病名称
     */
    private String diseaseName;

    /**
     * 确诊日期
     */
    private Date diagnosisDate;

    /**
     * 治疗总结
     */
    private String treatmentSummary;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}