package   com.his.master.controller;

import com.his.master.common.BaseResponse;
import com.his.master.common.ResultUtils;
import com.his.master.model.entity.SysMenu;
import com.his.master.model.entity.SysUser;
import com.his.master.model.vo.RouterVo;
import com.his.master.service.LoginServcie;
import com.his.master.service.SysMenuService;
import com.his.master.utils.security.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api("登陆接口")
public class LoginController {


    @Resource
    private LoginServcie loginServcie;


    @Resource
    private SysMenuService sysMenuService;

    /**
     * 用户登录
     * @param sysUser 用户实体
     * @return token
     */
    @PostMapping("/login")
    @ApiOperation("用户登陆")
    public BaseResponse<HashMap<String,String>> login(@RequestBody SysUser sysUser) {
        return ResultUtils.success(loginServcie.login(sysUser));
    }

    /**
     * 用户退出
     */
    @GetMapping("/logout")
    @ApiOperation("用户退出")
    public BaseResponse<Void> logout(){
        loginServcie.logout();
        return ResultUtils.success(null);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public BaseResponse<SysUser> getUserInfo(){
        SysUser sysUser = SecurityUtils.getUser();
        return ResultUtils.success(sysUser);
    }

    /**
     * 根据用户角色获取菜单
     */
    @GetMapping("/getRouter")
    @ApiOperation("根据用户角色获取菜单")
    public BaseResponse<List<SysMenu>> getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = sysMenuService.getMenuByUserId(userId);
        return ResultUtils.success(sysMenuService.buildMenuTree(menus));
    }

}