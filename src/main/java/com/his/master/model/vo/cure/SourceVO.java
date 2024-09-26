package com.his.master.model.vo.cure;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SourceVO {
    //时间总列表
    private List<SourceTImeVO> times;
    //日期id
    private Date date;
    //不可选时间列表
    private List<Integer> unTimes;
    //周几
    private Integer week;
}


