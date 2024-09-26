package com.his.master.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.his.master.mapper.MedPatientMedicalHistoryMasterMapper;
import com.his.master.model.dto.med.MedPatientsQueryDTO;
import com.his.master.model.dto.patient.MedPatientsAddDTO;
import com.his.master.model.entity.*;
import com.his.master.model.vo.patient.MedPatientsVO;
import com.his.master.mybatis.LambdaQueryWrapperX;
import com.his.master.service.CureMedicalRecordsService;
import com.his.master.service.MedPatientTagService;
import com.his.master.service.MedPatientTagsService;
import com.his.master.service.MedPatientsService;
import com.his.master.mapper.MedPatientsMapper;
import com.his.master.utils.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【med_patients(患者基本信息表)】的数据库操作Service实现
 * @createDate 2024-07-01 18:46:56
 */
@Service
public class MedPatientsServiceImpl extends ServiceImpl<MedPatientsMapper, MedPatients>
        implements MedPatientsService {

    @Resource
    private MedPatientsMapper medPatientsMapper;

    @Resource
    private MedPatientMedicalHistoryMasterMapper masterMapper;

    @Resource
    private CureMedicalRecordsService cureMedicalRecordsService;

    @Resource
    private MedPatientTagsService medPatientTagsService;

    @Resource
    private MedPatientTagService medPatientTagService;

    /**
     * 患者列表
     *
     * @param medPatients
     * @return
     */
    @Override
    public PageResult<MedPatientsVO> listPatients(MedPatientsQueryDTO medPatients) {
        PageResult<MedPatients> medPatientsPageResult = medPatientsMapper.listPatients(medPatients);
        //转换tag
        List<MedPatients> medPatientsList = medPatientsPageResult.getList();
        return new PageResult<>(buildMedPatientsVO(medPatientsList), medPatientsPageResult.getTotal());
    }


    /**
     * 根据患者id获取基础信息
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getPatientsById(Long id) {
        Map<String, Object> patientMap = new HashMap<>();
        // 获取患者的基础信息
        MedPatients patients = getById(id);
        patientMap.put("patients", patients);
        // 获取患者既往史
        MedPatientMedicalHistoryMaster previous = masterMapper.findByPatientId(id);
        patientMap.put("previous", previous);
        return patientMap;
    }

    /**
     * 患者新增
     *
     * @param medPatients
     * @return
     */
    @Override
    @Transactional
    public boolean addPatients(MedPatientsAddDTO medPatients) {
        MedPatients bean = BeanUtil.toBean(medPatients, MedPatients.class);
        //添加标签
        save(bean);
        medPatients.setPatientId(bean.getPatientId());
        return addTags(medPatients);
    }

    /**
     * 患者修改
     *
     * @param medPatients
     * @return
     */
    @Override
    public boolean updatePatients(MedPatients medPatients) {
        return updateById(medPatients);
    }

    /**
     * 患者删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deletePatients(Long id) {
        return removeById(id);
    }


    /**
     * 判断患者是否存在
     *
     * @param medPatients
     * @return
     */
    @Override
    public boolean isExistPatients(MedPatients medPatients) {
        return false;
    }

    @Override
    public MedPatients getPatientsByCardNo(String cardNo) {
        return getOne(new LambdaQueryWrapperX<MedPatients>().eq(MedPatients::getMedicalNumber,cardNo));
    }

    /**
     * 构建VO返回对象
     */
    private List<MedPatientsVO> buildMedPatientsVO(List<MedPatients> medPatients) {
        List<MedPatientsVO> medPatientsVOList = new ArrayList<>();
        for (MedPatients val : medPatients) {
            MedPatientsVO medPatientsVO = new MedPatientsVO();
            BeanUtil.copyProperties(val, medPatientsVO);
            if (!StrUtil.isEmpty(val.getTagName())) {
                List<String> list = Arrays.stream(val.getTagName().split(",")).collect(Collectors.toList());
                medPatientsVO.setTagName(list);
            }
            medPatientsVOList.add(medPatientsVO);
        }
        return medPatientsVOList;
    }

    private boolean addTags(MedPatientsAddDTO medPatientTags) {
        List<Long> tagName = medPatientTags.getTagName();
        Set<Long> tagIds = new HashSet<>(tagName);
        List<MedPatientTag> tags = new ArrayList<>();
        for (Long val : tagIds) {
            MedPatientTag medPatientTag = new MedPatientTag();
            medPatientTag.setPatientId(medPatientTags.getPatientId());
            medPatientTag.setTagId(val);
            tags.add(medPatientTag);

        }
        return medPatientTagService.saveBatch(tags);
    }
}




