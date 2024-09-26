package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 部门表
 * @TableName sys_dept
 */
@TableName(value ="sys_dept")
@Data
public class SysDept implements Serializable {
    /**
     * 科室id
     */
    @TableId(type = IdType.AUTO)
    private Long deptId;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 父部门id
     */
    private Long parentId;

    /**
     * 科室标记(0普通科室,1输液科室)
     */
    private Integer deptSign;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 部门状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}