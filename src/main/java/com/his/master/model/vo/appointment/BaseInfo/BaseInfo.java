package com.his.master.model.vo.appointment.BaseInfo;

import com.his.master.model.webservice.Brxx;
import com.his.master.model.webservice.TransfusionVO;
import lombok.Data;

import java.util.List;

@Data
public class BaseInfo {

    //患者基础信息
    private Brxx brxx;

    //患者药品信息
    private List<TransfusionVO> transfusionVOList;;
}
