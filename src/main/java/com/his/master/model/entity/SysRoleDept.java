package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色和部门关联表
 * @TableName sys_role_dept
 */
@TableName(value ="sys_role_dept")
@Data
public class SysRoleDept implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}