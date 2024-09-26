package   com.his.master.utils.security;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import   com.his.master.common.ErrorCode;
import   com.his.master.exception.BusinessException;
import   com.his.master.mapper.SysUserMapper;
import   com.his.master.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getStaffCode, username);
        SysUser user = userMapper.selectOne(wrapper);

        //如果没有该用户就抛出异常
        if (Objects.isNull(user)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR,"没有查询到用户");
        }

        // 用户被禁用
        if (user.getStatus().equals("1")){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR,"账号已被禁用");
        }

        //TODO: 查询权限信息封装到LoginUser中

        // 将用户信息封装到UserDetails实现类中
        return new LoginUser(user);
    }



}