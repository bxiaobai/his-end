package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName cure_treatment_plan_medicine_details
 */
@TableName(value ="cure_treatment_plan_medicine_details")
@Data
public class CureTreatmentPlanMedicineDetails implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 治疗方案详情表id
     */
    private Long detailId;

    /**
     * 药品名称
     */
    private String medicineName;

    /**
     * 规格
     */
    private String specification;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 开方人
     */
    private String prescriber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}