package   com.his.master.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.model.dto.user.AddAndUpdateUserDTO;
import com.his.master.model.dto.user.UserQueryDTO;
import   com.his.master.model.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user(用户信息表)】的数据库操作Service
* @createDate 2024-06-20 18:11:06
*/
public interface SysUserService extends IService<SysUser> {

    //获取所有用户列表
    Page<SysUser> listUser(UserQueryDTO userQueryDTO);

    //获取单个用户
    SysUser getUserById(Long id);

    //新增用户
    boolean addUser(AddAndUpdateUserDTO sysUser);

    //修改用户
    boolean updateUser(AddAndUpdateUserDTO sysUser);

    //删除用户
    boolean deleteUser(Long id);

    //校验用户名是否唯一
    boolean checkUserNameUnique(AddAndUpdateUserDTO userName);


    //检验是否可以删除
    void checkUserAllowed(SysUser sysUser);

    //修改用户密码
    boolean updateUserPwd(SysUser sysUser);
}
