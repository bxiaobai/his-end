package com.his.master.model.dto.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.his.master.common.PageRequest;
import com.his.master.mybatis.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryDTO extends PageParam {


    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 角色状态（0正常 1停用）
     */
    private String status;


}
