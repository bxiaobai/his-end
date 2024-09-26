package com.his.master.service;

import com.his.master.model.entity.CureFollowUpInfoDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【cure_follow_up_info_details】的数据库操作Service
* @createDate 2024-09-06 15:26:33
*/
public interface CureFollowUpInfoDetailsService extends IService<CureFollowUpInfoDetails> {

    /**
     * 添加随访想i去那个
     */
    boolean addFollowUpInfoDetails(CureFollowUpInfoDetails cureFollowUpInfoDetails);
}
