package com.his.master.model.dto.cure;

import com.baomidou.mybatisplus.annotation.*;
import com.his.master.model.entity.CureFollowUpInfoDetails;
import com.his.master.mybatis.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName cure_follow_up_info
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="cure_follow_up_info")
@Data
public class CureFollowUpInfoDTO extends PageParam implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 医疗记录表id
     */
    private Long medicalRecordId;

    /**
     * 患者id
     */
    private Long patientId;

    /**
     * 随访频次
     */
    private Long followUpFrequency;

    /**
     * 随访周期
     */
    private String followUpPeriod;

    /**
     * 随访方式
     */
    private String followUpMethod;

    /**
     * 已随访次数
     */
    private Long followUpTimes;

    /**
     * 剩余随访次数
     */
    private Long remainingFollowUpTimes;

    /**
     * 下次随访时间
     */
    private Date nextFollowUpDate;

    /**
     * 随访状态
     */
    private String followUpStatus;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private List<String> creationDate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 问卷状态
     */
    private String questionnaireStatus;

    /**
     * 问卷id
     */
    private Long questionnaireId;

    /**
     * 确认状态
     */
    private String confirmStatus;

    @TableField(exist = false)
    private List<CureFollowUpInfoDetails> cureFollowUpInfoDetails;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}