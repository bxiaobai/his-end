package com.his.master.model.dto.patient;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 患者基本信息表
 * @TableName med_patients
 */
@TableName(value ="med_patients")
@Data
public class MedPatientsAddDTO implements Serializable {
    /**
     * 患者ID
     */
    @TableId(type = IdType.AUTO)
    private Long patientId;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者卡号
     */
    private String medicalNumber;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birth;

    /**
     * 性别（0-男，1-女）
     */
    private Integer gender;

    /**
     * 婚姻状况（0-未婚，1-已婚，2-离异，3-丧偶）
     */
    private Integer maritalStatus;

    /**
     * 联系电话
     */
    private String phoneNumber;

    @TableField(exist = false)
    private List<Long> tagName;
    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 住址
     */
    private String address;

    /**
     * 紧急联系人姓名
     */
    private String emergencyContactName;

    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhone;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}