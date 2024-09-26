package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 输液室分配科室表
 * @TableName sys_room_dept
 */
@TableName(value ="sys_room_dept")
@Data
public class SysRoomDept implements Serializable {
    /**
     * 输液室id
     */
    @TableId
    private Long roomId;

    /**
     * 科室id
     */
    private Long deptId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}