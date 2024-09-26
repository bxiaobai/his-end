package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 手术史详情表
 * @TableName med_surgery_history_details
 */
@TableName(value ="med_surgery_history_details")
@Data
public class MedSurgeryHistoryDetails implements Serializable {
    /**
     * 手术史详情ID
     */
    @TableId(type = IdType.AUTO)
    private Long detailId;

    /**
     * 关联的既往史主表ID
     */
    private Long historyId;

    /**
     * 手术名称
     */
    private String surgeryName;

    /**
     * 手术日期
     */
    private Date surgeryDate;

    /**
     * 主刀医生
     */
    private String surgeon;

    /**
     * 手术结果（1-成功，2-失败，3-并发症）
     */
    private Integer outcome;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}