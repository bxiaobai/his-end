package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @TableName cure_medical_details
 */
@TableName(value ="cure_medical_details")
@Data
public class CureMedicalDetails implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(CureMedicalDetails.class);
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主表id
     */
    private Long medicalRecordId;

    /**
     * 接诊医生
     */
    private String attendingPhysician;

    /**
     * 接诊科室
     */
    private String department;

    /**
     * 接诊地点
     */
    private String location;

    /**
     * 接诊时间
     */
    @JsonFormat(timezone = "GMT+8" , pattern = "yyyy-MM-dd")
    private Date attendingTime;

    /**
     * 患者主诉
     */
    private String patientComplaint;

    /**
     * 现病史
     */
    private String presentIllness;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date creationDate;

    /**
     * 创建人
     */
    private String creator;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}