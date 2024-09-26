package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_yhxx
 */
@TableName(value ="t_yhxx")
@Data
public class TYhxx implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String yhdm;

    /**
     * 
     */
    private String yhkl;

    /**
     * 
     */
    private String yhxm;

    /**
     * 
     */
    private Integer yhlx;

    /**
     * 
     */
    private String yhdm1;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}