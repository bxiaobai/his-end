package com.his.master.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.model.entity.CureFollowUpInfoDetails;
import com.his.master.service.CureFollowUpInfoDetailsService;
import com.his.master.mapper.CureFollowUpInfoDetailsMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【cure_follow_up_info_details】的数据库操作Service实现
* @createDate 2024-09-06 15:26:33
*/
@Service
public class CureFollowUpInfoDetailsServiceImpl extends ServiceImpl<CureFollowUpInfoDetailsMapper, CureFollowUpInfoDetails>
    implements CureFollowUpInfoDetailsService{

    @Override
    public boolean addFollowUpInfoDetails(CureFollowUpInfoDetails cureFollowUpInfoDetails) {
        return save(cureFollowUpInfoDetails);
    }
}




