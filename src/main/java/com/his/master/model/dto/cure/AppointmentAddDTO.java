package com.his.master.model.dto.cure;

import lombok.Data;

import java.util.List;

@Data
public class AppointmentAddDTO {
    private Long deptId;
    private List<String> dataList;
}
