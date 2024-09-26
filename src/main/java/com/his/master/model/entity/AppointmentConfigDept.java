package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 号源配置科室表
 * @TableName appointment_config_dept
 */
@TableName(value ="appointment_config_dept")
@Data
public class AppointmentConfigDept implements Serializable {
    /**
     * 配置id
     */
    private Long configId;
    /**
     * 科室id
     */
    private Long deptId;

    private Integer weekId;

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}