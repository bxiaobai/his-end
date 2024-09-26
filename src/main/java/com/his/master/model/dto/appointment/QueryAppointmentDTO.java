package com.his.master.model.dto.appointment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.his.master.mybatis.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryAppointmentDTO extends PageParam implements Serializable {

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
     * 预约状态（0已预约、1人工取消、2患者取消）
     */
    private Integer status;

    /**
     * 下次治疗时间
     */
    private String nextTreatTime;


}
