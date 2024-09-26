package com.his.master.model.webservice;

import lombok.Data;

import java.util.List;

@Data
public class TransfusionVO {

    private String creationTime;
    //需要输液天数
    private Integer infusionTotal;
    //已输液天数
    private Integer infusionNum;
    //状态
    private Integer status;
    private List<Yzxxs> yzxxsList;
    private Integer infusionTime;
    private String createDoctor;
    private Boolean isCharge;
    private String infusionDays;
    private String kfksmc;
}
