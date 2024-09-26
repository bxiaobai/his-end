package com.his.master.model.dto.survey;

import com.his.master.mybatis.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurveyQueryDTO extends PageParam {
    private String surveyName;

}
