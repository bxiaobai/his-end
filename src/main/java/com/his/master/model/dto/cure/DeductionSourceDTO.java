package com.his.master.model.dto.cure;

import lombok.Data;

import java.util.List;

@Data
public class DeductionSourceDTO {
    private List<Long> sourceId;
    private Integer sourceType;
    private String date;
    private Long roomId;
    //如果点击座位重新发起查询,根据座位号科室id时间查询预约列表中是否有数据，如果有就返回量
    private Long seatId;
    //如果点击时间重新发起查询
    private List<String> timeId;
}
