package com.his.master.model.dto.patient;

import lombok.Data;

import java.util.List;

@Data
public class AddOrUpdateTagDTO {

    private String tagName;

    private Long tagId;

    private Long patientId;

}
