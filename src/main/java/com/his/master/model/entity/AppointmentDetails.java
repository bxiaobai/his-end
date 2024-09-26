package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 预约信息表
 * @TableName appointment_details
 */
@TableName(value ="appointment_details")
@Data
public class AppointmentDetails implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 预约人
     */
    private String appointee;

    /**
     * 预约卡号
     */
    private String cardNumber;

    /**
     * 预约日期
     */
    @JsonFormat( pattern = "yyyy-MM-dd" , timezone="GMT+8")
    private Date appointDate;

    /**
     * 预约座位id
     */
    private Long seatId;

    /**
     * 座位号
     */
    private String seatNumber;

    /**
     * 预约时间
     */
    private String appointTime;

    /**
     * 患者类型
     */
    private String patientType;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 号源类型
     */
    private Integer sourceType;

    /**
     * 操作人id
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 输液室id
     */
    private Long infusionId;

    /**
     * 输液室名称
     */
    private String infusionName;

    /**
     * 患者手机号
     */
    private String phoneNumber;

    /**
     * 所属号源id
     */
    private String sourceId;

    /**
     * 预约状态（0已预约、1人工取消、2患者取消）
     */
    private Integer status;

    /**
     * 排队号
     */
    private String queueNumber;

    /**
     * 流水号
     */
    private Long serialNumber;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 下次治疗时间
     */
    private Date nextTreatTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}