package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName cure_chemo_daily_medication
 */
@TableName(value ="cure_chemo_daily_medication")
@Data
public class CureChemoDailyMedication implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 化疗记录主表id
     */
    private Long chemotherapyRecordId;

    /**
     * 药品名称
     */
    private String medicationName;

    /**
     * 规格
     */
    private String specification;

    /**
     * 数量
     */
    private Long quantity;

    /**
     * 开方人
     */
    private String prescriber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}