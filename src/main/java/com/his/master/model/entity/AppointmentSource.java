package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 号源表
 * @TableName appointment_source
 */
@TableName(value ="appointment_source")
@Data
public class AppointmentSource implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long sourceId;

    /**
     * 日期
     */
    private Date configDate;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 号源总数
     */
    private Integer sourceTotal;

    /**
     * 剩余数量
     */
    private Integer remainingCount;


    /**
     * 每日号源状态
     */
    private String status;

    /**
     * 临时号源
     */
    private Integer standbyNumber;

    /**
     * 输液室id
     */
    private Long transfusionId;

    /**
     * 时间类型
     */
    private String timeClass;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}