package   com.his.master.service;


import   com.his.master.model.entity.SysUser;

import java.util.HashMap;

public interface LoginServcie {

    //登陆生成token
    HashMap<String,String> login(SysUser user);

    //登出
    void logout();

    //根据用户查询菜单列表
    void getMenuList(Long userId);


//
//    ResponseResult login(SysUser user);
//
//    ResponseResult logout();
}