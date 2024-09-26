package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * 患者既往史主表
 * @TableName med_patient_medical_history_master
 */
@TableName(value ="med_patient_medical_history_master")
@Data
public class MedPatientMedicalHistoryMaster implements Serializable {
    /**
     * 既往史记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long historyId;

    /**
     * 患者ID，外键关联患者基本信息表
     */
    private Long patientId;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 是否有效
     */
    private Integer isActive;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 过敏史记录
     */
    @TableField(exist = false)
    private List<MedAllergyHistoryDetails> allergicHistory;

    /**
     * 疾病史记录
     */
    @TableField(exist = false)
    private List<MedDiseaseHistoryDetails> diseaseHistory;

    /**
     * 手术史记录
     */
    @TableField(exist = false)
    private List<MedSurgeryHistoryDetails> surgeryHistoryDetailsList;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}