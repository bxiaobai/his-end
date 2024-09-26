package com.his.master.model.dto.cure;

import lombok.Data;

import java.util.List;

@Data
public class DeptConfigDTO {
    private Long deptId;
    private List<Long> configIds;
    private Integer weekId;
}
