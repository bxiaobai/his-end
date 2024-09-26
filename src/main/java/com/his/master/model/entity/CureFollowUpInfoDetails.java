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
 * @TableName cure_follow_up_info_details
 */
@TableName(value ="cure_follow_up_info_details")
@Data
public class CureFollowUpInfoDetails implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 随访信息表id
     */
    private Long followUpInfoId;

    /**
     * 信息发送时间
     */
    private Date informationSendTime;

    /**
     * 发送方式
     */
    private String sendMethod;

    /**
     * 发送状态
     */
    private String sendStatus;

    /**
     * 随访完成时间
     */
    private Date followUpCompletionTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}