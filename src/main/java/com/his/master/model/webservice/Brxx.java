package com.his.master.model.webservice;

import cn.hutool.core.date.DateUtil;
import com.his.master.model.entity.MedPatients;
import lombok.Data;

@Data
public class Brxx {

    private String Blh;
    private int Brlx;
    private String Brxb;
    private String Brxm;
    private String Csrq;
    private String Gzdw;
    private String Jtzz;
    private String Lxdh;
    private String Lxfs;
    private String gms;


    /**
     * 转换病人信息
     */
    public void convert(MedPatients medPatients) {
        this.Blh = medPatients.getMedicalNumber();
        this.Brlx = medPatients.getPatientType();
        this.Brxb = medPatients.getGender() == 0 ? "男" : "女";
        this.Brxm = medPatients.getPatientName();
        this.Csrq = DateUtil.format(medPatients.getBirth(),"yyyy-MM-dd");
        this.Gzdw = medPatients.getWorkUnit();
        this.Jtzz = medPatients.getAddress();
        this.Lxdh = medPatients.getPhoneNumber();
        this.Lxfs = medPatients.getPhoneNumber();
    }
}
