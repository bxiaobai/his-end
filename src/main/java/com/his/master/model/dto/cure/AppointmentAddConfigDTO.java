package com.his.master.model.dto.cure;

import lombok.Data;

import java.util.List;

@Data
public class AppointmentAddConfigDTO {
    private List<Long> weekId;
    private Long deptId;
}
