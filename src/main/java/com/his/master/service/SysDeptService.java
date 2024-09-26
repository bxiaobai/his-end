package com.his.master.service;

import com.his.master.model.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.model.vo.dept.DeptTreeVO;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_dept(部门表)】的数据库操作Service
* @createDate 2024-06-26 18:04:13
*/
public interface SysDeptService extends IService<SysDept> {

    List<DeptTreeVO> listTreeDept();

    List<SysDept> listDept();

    List<DeptTreeVO> postTreeDept(SysDept sysDept);
}
