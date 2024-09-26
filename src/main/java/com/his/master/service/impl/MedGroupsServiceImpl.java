package com.his.master.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.mapper.MedPatientGroupMapper;
import com.his.master.model.entity.MedGroups;
import com.his.master.model.entity.MedPatientGroup;
import com.his.master.service.MedGroupsService;
import com.his.master.mapper.MedGroupsMapper;
import com.his.master.service.MedPatientGroupService;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【med_groups(分组信息表)】的数据库操作Service实现
 * @createDate 2024-07-02 15:45:11
 */
@Service
public class MedGroupsServiceImpl extends ServiceImpl<MedGroupsMapper, MedGroups>
        implements MedGroupsService {


    @Resource
    private MedPatientGroupMapper medPatientGroupMapper;

    /**
     * 分组列表
     *
     * @param medGroups
     * @return
     */
    @Override
    public Page<MedGroups> listGroups(MedGroups medGroups) {
        Page<MedGroups> page = new Page<>(medGroups.getCurrent(), medGroups.getPageSize());
        return this.page(page, buildLambdaQueryWrapper(medGroups));
    }

    /**
     * 添加分组
     *
     * @param medGroups
     * @return
     */
    @Override
    public boolean addGroups(MedGroups medGroups) {
        return save(medGroups);
    }

    /**
     * 删除分组
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteGroups(Long id) {
        return removeById(id);
    }

    /**
     * 修改分组
     *
     * @param medGroups
     * @return
     */
    @Override
    public boolean updateGroups(MedGroups medGroups) {
        return updateById(medGroups);
    }

    /**
     * 根据id查询分组
     *
     * @param id
     * @return
     */
    @Override
    public MedGroups getGroupsById(Long id) {
        return getById(id);
    }

    /**
     * 判断分组是否有患者存在
     *
     * @param medGroups
     * @return
     */
    @Override
    public boolean isExistGroupsPatients(MedGroups medGroups) {
        Long groupId = medGroups.getGroupId();
        LambdaQueryWrapper<MedPatientGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MedPatientGroup::getGroupId, groupId);
        Long count = medPatientGroupMapper.selectCount(lambdaQueryWrapper);
        return count == 0;
    }

    /**
     * 判断分组名称是否存在
     *
     * @param medGroups 待检查的分组信息
     * @return true 如果分组名称已存在且不是当前记录自身，否则false
     */
    @Override
    public boolean isExistGroupsName(MedGroups medGroups) {
        if (medGroups == null || StrUtil.isEmpty(medGroups.getGroupName())) {
            return false; // 如果传入对象为null或groupName为空，直接返回false表示不存在
        }
        String groupName = medGroups.getGroupName();
        Long currentGroupId = medGroups.getGroupId(); // 假设getGroupId()返回的是Long类型，需处理null情况
        LambdaQueryWrapper<MedGroups> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MedGroups::getGroupName, groupName);
        MedGroups one = getOne(lambdaQueryWrapper);
        // 当查询到记录且不是当前记录自身时，返回true表示名称已存在
        return one != null && (!Objects.equals(currentGroupId, one.getGroupId()) || Objects.isNull(currentGroupId));
    }


    /**
     * 构建查询条件
     */
    public LambdaQueryWrapper<MedGroups> buildLambdaQueryWrapper(MedGroups medGroups) {
        LambdaQueryWrapper<MedGroups> queryWrapper = new LambdaQueryWrapper<>();
        if (!StrUtil.isEmpty(medGroups.getGroupName())) {
            queryWrapper.like(MedGroups::getGroupName, medGroups.getGroupName());
        }
        return queryWrapper;
    }

    /**
     * 获取所有分组
     *
     * @return
     */
    @Override
    public List<MedGroups> listAllGroups() {
        return list();
    }

    /**
     * 获取患者分组
     *
     * @param patientId
     * @return
     */
    @Override
    public List<Long> getPatientGroups(Long patientId) {
        LambdaQueryWrapper<MedPatientGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MedPatientGroup::getPatientId, patientId);
        List<MedPatientGroup> medPatientGroups = medPatientGroupMapper.selectList(lambdaQueryWrapper);
        if (!medPatientGroups.isEmpty()) {
            return medPatientGroups.stream().map(MedPatientGroup::getGroupId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 添加患者和分组关联
     *
     * @param patientId
     * @param groupId
     * @return
     */
    @Override
    public void addPatientGroups(Long patientId, List<Long> groupId) {
        //删除所有患者和分组关联
        deletePatientGroups(patientId);
        if (!groupId.isEmpty()){
            //添加新患者和分组关联
            for (Long id : groupId){
                MedPatientGroup medPatientGroup = new MedPatientGroup();
                medPatientGroup.setPatientId(patientId);
                medPatientGroup.setGroupId(id);
                medPatientGroupMapper.insert(medPatientGroup);
            }
        }
    }

    @Override
    public void deletePatientGroups(Long patientId) {
        medPatientGroupMapper.delete(new LambdaQueryWrapper<MedPatientGroup>().eq(MedPatientGroup::getPatientId, patientId));
    }
}




