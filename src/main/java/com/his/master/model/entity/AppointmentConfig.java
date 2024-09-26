package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.his.master.mybatis.PageParam;
import lombok.Data;

/**
 * 号源模板主表
 * @TableName appointment_config
 */
@TableName(value ="appointment_config")
@Data
public class AppointmentConfig implements Serializable {
    /**
     * 配置id
     */
    @TableId(type = IdType.AUTO)
    private Long configId;

    /**
     * 时间类型（0上午，1下午）
     */
    private String timeClass;

    /**
     * 开始时间
     */
    @JsonFormat( pattern = "HH:mm" , timezone="GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "HH:mm" , timezone="GMT+8")
    private Date endTime;

    /**
     * 号源数量
     */
    private Integer sourceNumber;

    /**
     * 临时号源
     */
    private Integer standbyNumber;

    /**
     * 患者类型(1急诊,2门诊)
     */
    private String medicalType;


    /**
     * 号源配置状态
     */
    private String status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}