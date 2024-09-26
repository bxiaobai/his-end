package com.his.master.model.dto.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.his.master.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageLogDTO extends PageRequest {
    /**
     * 操作人员
     */
    private String operName;
    /**
     * 主机地址
     */
    private String operIp;
    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作状态（0正常 1异常）
     */
    private String status;

    /**
     * 操作时间
     */
    private Date operTime;
    private String title;

}
