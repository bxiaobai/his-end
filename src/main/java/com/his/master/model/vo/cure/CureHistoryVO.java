package com.his.master.model.vo.cure;

import lombok.Data;

@Data
public class CureHistoryVO {

    private Long id;

    private String name;
//    首次开始日期，治疗类型、结果、随访状态、
    private String firstStartDate;

    private String treatmentType;

    private String result;

    private String followUpStatus;


}
