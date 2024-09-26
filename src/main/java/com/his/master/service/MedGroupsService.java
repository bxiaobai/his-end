package com.his.master.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.his.master.model.entity.MedGroups;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【med_groups(分组信息表)】的数据库操作Service
* @createDate 2024-07-02 15:45:11
*/
public interface MedGroupsService extends IService<MedGroups> {

    /**
     * 分页查询所有分组
     */
    Page<MedGroups> listGroups(MedGroups medGroups);

    /**
     * 添加分组
     */
    boolean addGroups(MedGroups medGroups);

    /**
     * 删除分组
     */
    boolean deleteGroups(Long id);

    /**
     * 修改分组
     */
    boolean updateGroups(MedGroups medGroups);

    /**
     * 根据id查询分组
     */
    MedGroups getGroupsById(Long id);

    /**
     * 检验分组下方是否有患者
     */
    boolean isExistGroupsPatients(MedGroups medGroups);

    /**
     * 校验分组名称是否重复
     */
    boolean isExistGroupsName(MedGroups medGroups);

    /**
     * 查询全部分组
     */
    List<MedGroups> listAllGroups();

    /**
     * 获取患者所属分组
     */
    List<Long> getPatientGroups(Long patientId);

    /**
     * 添加患者和分组的关联
     */
    void addPatientGroups(Long patientId, List<Long> groupId);

    /**
     * 删除患者和分组所有关联
     */
    void deletePatientGroups(Long patientId);
}
