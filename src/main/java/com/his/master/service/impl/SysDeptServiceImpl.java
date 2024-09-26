package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.SysDept;
import com.his.master.model.vo.dept.DeptTreeVO;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.SysDeptService;
import com.his.master.mapper.SysDeptMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_dept(部门表)】的数据库操作Service实现
 * @createDate 2024-06-26 18:04:13
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept>
        implements SysDeptService {

    @Override
    public List<DeptTreeVO> listTreeDept() {
        List<SysDept> list = list();
        return buildTree(list);
    }

    @Override
    public List<SysDept> listDept() {
        return list();
    }

    @Override
    public List<DeptTreeVO> postTreeDept(SysDept sysDept) {
        LambdaQueryWrapperX<SysDept> lambdaQueryWrapperX = new LambdaQueryWrapperX<>();
        lambdaQueryWrapperX.likeIfPresent(SysDept::getDeptName, sysDept.getDeptName());
        List<SysDept> list = list(lambdaQueryWrapperX);
        return buildTree(list);
    }

    //构建树形结构
    public List<DeptTreeVO> buildTree(List<SysDept> list) {
        List<SysDept> treeList = list.stream().filter(item -> item.getParentId() == 0).collect(Collectors.toList());
        List<SysDept> collect = list.stream().filter(item -> item.getParentId() != 0).collect(Collectors.toList());
        List<DeptTreeVO> tree = new ArrayList<>();
        for (SysDept item : treeList) {
            DeptTreeVO deptTreeVO = new DeptTreeVO();
            deptTreeVO.setDeptId(item.getDeptId());
            deptTreeVO.setDeptName(item.getDeptName());
            List<DeptTreeVO> deptTreeVOList1 = buildTree(collect, item.getDeptId());
            deptTreeVO.setChildren(deptTreeVOList1);
            tree.add(deptTreeVO);
        }
        return tree;
    }

    private List<DeptTreeVO> buildTree(List<SysDept> list, Long parentId) {
        return list.stream().filter(item -> item.getParentId().equals(parentId)).map(item -> {
            DeptTreeVO deptTreeVO = new DeptTreeVO();
            deptTreeVO.setDeptId(item.getDeptId());
            deptTreeVO.setDeptName(item.getDeptName());
            deptTreeVO.setChildren(buildTree(list, item.getDeptId()));
            return deptTreeVO;
        }).collect(Collectors.toList());
    }
}

