package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.his.master.mybatis.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 输液室表
 * @TableName appointment_room
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="appointment_room")
@Data
public class AppointmentRoom  extends PageParam implements Serializable {
    /**
     * 输液室id
     */
    @TableId(type = IdType.AUTO)
    private Long transfusionId;

    /**
     * 输液室名称
     */
    private String transfusionName;

    /**
     * 患者类型(1急诊,2门诊)
     */
    private String medicalType;

    /**
     * 输液室状态（0正常 1停用）
     */
    private String status;

    /**
     * 输液地点
     */
    private String infuSite;

    /**
     * 取号点
     */
    private String numSite;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 短信回复内容
     */
    private String message;

    @TableField(exist = false)
    private List<AppointmentSeat> appointmentSeats;

    //不可选座位
    @TableField(exist = false)
    private List<Long> unSeats;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}