package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 过敏史详情表
 * @TableName med_allergy_history_details
 */
@TableName(value ="med_allergy_history_details")
@Data
public class MedAllergyHistoryDetails implements Serializable {
    /**
     * 过敏史详情ID
     */
    @TableId(type = IdType.AUTO)
    private Long detailId;

    /**
     * 关联的既往史主表ID
     */
    private Long historyId;

    /**
     * 过敏原
     */
    private String allergen;

    /**
     * 过敏反应描述
     */
    private String reactionDescription;

    /**
     * 过敏严重程度（1-轻度，2-中度，3-重度）
     */
    private Integer severity;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}