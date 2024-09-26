package com.his.master.model.vo.cure;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SourceTImeVO {
    //号源id
    private Long sourceId;
    //时间列表
    private List<Integer> times;
    //剩余正常号源
    private Integer remainingCount;
    //剩余临时号源
    private Integer standbyNumber;
}
