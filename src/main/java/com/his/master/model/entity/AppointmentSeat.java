package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.his.master.mybatis.PageParam;
import lombok.Data;

/**
 * 座位表
 * @TableName appointment_seat
 */
@TableName(value ="appointment_seat")
@Data
public class AppointmentSeat extends PageParam implements Serializable {
    /**
     * 座位id
     */
    @TableId(type = IdType.AUTO)
    private Long seatId;

    /**
     * 座位号
     */
    private String seatNumber;

    /**
     * 大厅id
     */
    private Long transfusionId;

    /**
     * 患者类型(1急诊,2门诊)
     */
    private String medicalType;

    /**
     * 输液室状态（0正常 1停用）
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}