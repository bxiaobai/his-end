package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName cure_adverse_reactions
 */
@TableName(value ="cure_adverse_reactions")
@Data
public class CureAdverseReactions implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 治疗后信息id
     */
    private Long postTreatmentInfoId;

    /**
     * 发生时间
     */
    private Date occurrenceTime;

    /**
     * 介绍
     */
    private String description;

    /**
     * 处理时间
     */
    private Date managementTime;

    /**
     * 处理人
     */
    private String manager;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}