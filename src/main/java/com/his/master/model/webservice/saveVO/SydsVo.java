package com.his.master.model.webservice.saveVO;

import com.his.master.model.webservice.Yzxxs;
import lombok.Data;

import java.util.List;

@Data
public class SydsVo {
    private List<Yzxxs> Yzxxs;
    private String kbbz;
    private String ps;
}
