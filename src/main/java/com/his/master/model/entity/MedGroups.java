package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.his.master.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分组信息表
 * @TableName med_groups
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="med_groups")
@Data
public class MedGroups  extends PageRequest implements Serializable{
    /**
     * 分组ID
     */
    @TableId(type = IdType.AUTO)
    private Long groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组描述
     */
    private String description;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}