package com.his.master.model.vo.dept;

import lombok.Data;

import java.util.List;

@Data
public class DeptTreeVO {

    private String deptName;

    private Long deptId;

    private List<DeptTreeVO> children;
}
