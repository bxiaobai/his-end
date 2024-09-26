package   com.his.master.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 用户信息表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String staffCode;

    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型（00系统用户）
     */
    private String userType;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 创建者
     */
    private String createBy;

    @TableField(exist = false)
    private List<Long> roleIds;

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public SysUser() {
    }

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否为管理员
     * @param userId
     * @return
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

}