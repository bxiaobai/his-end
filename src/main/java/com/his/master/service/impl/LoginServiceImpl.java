package   com.his.master.service.impl;


import cn.hutool.core.util.StrUtil;
import   com.his.master.common.ErrorCode;
import   com.his.master.exception.BusinessException;
import com.his.master.model.entity.SysMenu;
import   com.his.master.model.entity.SysUser;
import   com.his.master.service.LoginServcie;
import com.his.master.service.SysMenuService;
import com.his.master.service.SysUserService;
import   com.his.master.utils.jwt.JwtUtil;
import   com.his.master.utils.redis.RedisCache;
import   com.his.master.utils.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginServcie {

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    RedisCache redisCache;

    @Resource
    private SysMenuService sysMenuService;


    @Override
    public  HashMap<String, String> login(SysUser user) {

        //登陆前校验
        loginPreCheck(user.getStaffCode(), user.getPassword());

        //1.封装Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getStaffCode(), user.getPassword());
        //2.通过AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        //3.在Authentication中获取用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        //4.认证通过生成token
        String jwt = JwtUtil.createJWT(userId);
        //5.用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        //6.把token返回给前端
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", jwt);
        return hashMap;
    }

    @Override
    public void logout() {
        //获取SecurityContextHolder中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getUserId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:" + userId);
    }


    /**
     * 登录前置校验
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password) {
        // 用户名或密码为空 错误
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或密码为空");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < 4 || password.length() > 12) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度6-12位之间");
        }

    }


    /**
     * 获取用菜单树
     */
    @Override
    public void getMenuList(Long userId) {
        //判断是不是管理员
        if (SysUser.isAdmin(userId)) {
            sysMenuService.getAllMenu(null);
        }else {
            sysMenuService.getMenuByUserId(userId);
        }
    }


}