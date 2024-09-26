package com.his.master.model.vo.patient;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.his.master.mybatis.PageParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 患者基本信息表
 */
@Data
public class MedPatientsVO extends PageParam {
    /**
     * 患者ID
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    private List<String> tagName;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}