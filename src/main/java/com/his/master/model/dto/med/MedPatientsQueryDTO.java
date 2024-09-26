package com.his.master.model.dto.med;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.his.master.common.PageRequest;
import com.his.master.mybatis.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class MedPatientsQueryDTO extends PageParam {

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 患者卡号
     */
    private String medicalNumber;


    /**
     * 患者标签
     */
    private String tagName;

    /**
     * 患者分组
     */
    private String group;

    /**
     * 下次就诊时间
     */
    private Date nextVisitTime;

    /**
     * 随访状态
     */
    private Integer followUpStatus;
}
