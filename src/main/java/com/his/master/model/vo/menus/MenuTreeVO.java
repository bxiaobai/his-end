package com.his.master.model.vo.menus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MenuTreeVO {

    private Long menuId;

    private String menuName;

    private String name;

    private Long parentId;

    private Long meta;

    private List<MenuTreeVO> children;
}
