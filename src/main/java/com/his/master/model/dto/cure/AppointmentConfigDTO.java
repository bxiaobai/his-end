package com.his.master.model.dto.cure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.his.master.mybatis.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 */
@Data
public class AppointmentConfigDTO extends PageParam implements Serializable {
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
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

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

    /**
     * 输液室id
     */
    private Integer transfusionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}