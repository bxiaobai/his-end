package com.his.master.model.dto.appointment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.his.master.model.entity.AppointmentDrug;
import com.his.master.model.webservice.TransfusionVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AddAppointmentDTO implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 预约人
     */
    private String appointee;

    /**
     * 预约卡号
     */
    private String cardNumber;

    /**
     * 预约日期
     */
    private String appointDate;

    /**
     * 预约座位id
     */
    private Long seatId;

    /**
     * 座位号
     */
    private String seatNumber;

    /**
     * 预约时间
     */
    private List<String> appointTime;

    /**
     * 患者类型
     */
    private String patientType;

    /**
     * 号源类型
     */
    private Integer sourceType;

    /**
     * 操作人id
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 输液室id
     */
    private Long infusionId;

    /**
     * 输液室名称
     */
    private String infusionName;

    /**
     * 患者手机号
     */
    private String phoneNumber;

    /**
     * 所属号源id
     */
    private List<Long> sourceId;

    /**
     * 预约状态（0已预约、1人工取消、2患者取消）
     */
    private Integer status;

    /**
     * 排队号
     */
    private String queueNumber;

    /**
     * 流水号
     */
    private Long serialNumber;

    /**
     *
     */
    private Integer isDelete;

    /**
     * 接收得药品
     */
    List<TransfusionVO> transfusionVOS;
    /**
     * 保存标志
     */
    private Integer saveStatus;
    
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
