package com.his.master.service;

import com.his.master.model.dto.cure.CureFollowUpInfoDTO;
import com.his.master.model.entity.CureFollowUpInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.his.master.utils.PageResult;

/**
* @author Administrator
* @description 针对表【cure_follow_up_info】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureFollowUpInfoService extends IService<CureFollowUpInfo> {

    /**
     * 添加随访信息
     * @param cureFollowUpInfo
     * @return
     */
    boolean addFollowUpInfo(CureFollowUpInfo cureFollowUpInfo);

    /**
     * 删除随访信息
     * @param id
     * @return
     */
    boolean deleteFollowUpInfo(Long id);

    /**
     * 修改随访信息
     * @param cureFollowUpInfo
     * @return
     */
    boolean updateFollowUpInfo(CureFollowUpInfo cureFollowUpInfo);

    boolean isExist(Long medicalRecordId);

    PageResult<CureFollowUpInfo> getFollowUpInfo(CureFollowUpInfoDTO cureFollowUpInfo);


    CureFollowUpInfo getCureFollowUpInfoById(Long id);
}
