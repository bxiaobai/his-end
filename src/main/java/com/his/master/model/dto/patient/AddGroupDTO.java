package com.his.master.model.dto.patient;

import lombok.Data;

import java.util.List;

@Data
public class AddGroupDTO {

    private Long patientId;

    private List<Long> groupsId;
}
