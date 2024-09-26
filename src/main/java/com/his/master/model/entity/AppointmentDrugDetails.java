package com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName appointment_drug_details
 */
@TableName(value ="appointment_drug_details")
@Data
public class AppointmentDrugDetails implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String sjly;

    /**
     * 
     */
    private String cfsbh;

    /**
     * 
     */
    private String ds;

    /**
     * 
     */
    private String gg;

    /**
     * 
     */
    private String jl;

    /**
     * 
     */
    private String pd;

    /**
     * 
     */
    private Integer psxx;

    /**
     * 
     */
    private String ypmc;

    /**
     * 
     */
    private Integer zId;

    /**
     * 
     */
    private String cfls;

    /**
     * 
     */
    private Integer kkcbz;

    /**
     * 
     */
    private Integer fysycs;

    /**
     * 
     */
    private Integer dsybz;

    /**
     * 
     */
    private Integer sl;

    /**
     * 
     */
    private Integer psjg;

    /**
     * 
     */
    private Integer yf;

    /**
     * 
     */
    private String ypid;

    /**
     * 
     */
    private Integer ypcs;

    /**
     * 
     */
    private String sydh;

    /**
     * 
     */
    private Integer sycs;

    /**
     * 
     */
    private String bzjl;

    /**
     * 
     */
    private String yfms;

    /**
     * 
     */
    private Integer kssbz;

    /**
     * 
     */
    private String ylts;

    /**
     * 
     */
    private Integer pcdm;

    /**
     * 
     */
    private String yytj;

    /**
     * 
     */
    private String kbbz;

    /**
     * 
     */
    private Integer jpid;

    /**
     * 
     */
    private String ps;

    /**
     * 
     */
    private String bz;

    /**
     * 
     */
    private Integer zby;

    /**
     * 
     */
    private Integer sfzt;

    /**
     * 
     */
    private String dose;

    /**
     * 
     */
    private String unit;

    /**
     * 
     */
    private Integer sysc;

    /**
     * 药品id
     */
    private Long drugId;

    /**
     * 
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}