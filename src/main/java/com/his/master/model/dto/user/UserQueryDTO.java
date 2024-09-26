package com.his.master.model.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.his.master.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDTO extends PageRequest {

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String staffCode;


    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;


}
