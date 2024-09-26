package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 预约药品记录
 * @TableName appointment_drug
 */
@TableName(value ="appointment_drug")
@Data
public class AppointmentDrug implements Serializable {
    /**
     * 药品id
     */
    @TableId(type = IdType.AUTO)
    private Long drugId;

    /**
     * 记录id
     */
    private Long resId;

    /**
     * 流水号
     */
    private String registerId;

    /**
     * 输液单号拼接
     */
    private String infusionApplyNos;

    /**
     * 药品创建时间
     */
    private String djsj;

    /**
     * 开方医生
     */
    private String createDoctor;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 药品详情
     */
    @TableField(exist = false)
    private List<AppointmentDrugDetails> appointmentDrugDetails;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}