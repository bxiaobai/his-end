package com.his.master.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.his.master.common.ErrorCode;
import com.his.master.exception.BusinessException;
import com.his.master.model.dto.appointment.AddAppointmentDTO;
import com.his.master.model.dto.appointment.QueryAppointmentDTO;
import com.his.master.model.dto.cure.DeductionSourceDTO;
import com.his.master.model.entity.*;
import com.his.master.model.vo.appointment.BaseInfo.BaseInfo;
import com.his.master.model.webservice.*;
import com.his.master.model.webservice.removeVO.RemoveVO;
import com.his.master.model.webservice.saveVO.SaveYySydjVO;
import com.his.master.model.webservice.saveVO.SydsVo;
import com.his.master.service.*;
import com.his.master.mapper.AppointmentDetailsMapper;
import com.his.master.utils.DateUtils;
import com.his.master.utils.PageResult;
import com.his.master.utils.security.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.xml.xpath.XPathConstants;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【appointment_details(预约信息表)】的数据库操作Service实现
 * @createDate 2024-09-19 10:48:25
 */
@Service
public class AppointmentDetailsServiceImpl extends ServiceImpl<AppointmentDetailsMapper, AppointmentDetails>
        implements AppointmentDetailsService {

    private static final String host = "8090";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Gson gson = new Gson();

    @Resource
    private AppointmentDetailsMapper appointmentDetailsMapper;

    @Resource
    private AppointmentSourceService appointmentSourceService;

    @Resource
    private TYhxxService tYhxxService;

    @Resource
    private AppointmentDrugService appointmentDrugService;

    @Resource
    private MedPatientsService medPatientsService;

    @Resource
    private AppointmentRoomService appointmentRoomService;


    @Override
    public PageResult<AppointmentDetails> pageAppointment(QueryAppointmentDTO appointmentDetails) {
        return appointmentDetailsMapper.pageAppointment(appointmentDetails);
    }

    @Override
    @Transactional
    public boolean addAppointment(AddAppointmentDTO addAppointmentDTO) {
        AppointmentDetails appointmentDetails = new AppointmentDetails();
        BeanUtil.copyProperties(addAppointmentDTO, appointmentDetails);
        //将添加的时间转为字符串用,拼接
        String appointDate = addAppointmentDTO.getAppointDate();
        List<String> appointTime = addAppointmentDTO.getAppointTime();
        //拼接时间字符串
        String timeStr = String.join(", ", appointTime);
        appointmentDetails.setAppointTime(timeStr);
        //操作人
        String user = SecurityUtils.getUser().getStaffCode();
        //操作人id
        Long userId = SecurityUtils.getUserId();
        appointmentDetails.setOperatorId(userId);
        TYhxx one = tYhxxService.getOne(new LambdaQueryWrapper<TYhxx>().eq(TYhxx::getYhdm, user));
        appointmentDetails.setOperatorName(one.getYhxm());
        //排队号
        String pdh = buildPdh(appointDate, appointTime);
        appointmentDetails.setQueueNumber(pdh);
        //下次治疗时间时预约时间 + 30天
        appointmentDetails.setNextTreatTime(DateUtil.offsetDay(appointmentDetails.getAppointDate(), 30));
        //扣减号源
        this.dedSource(addAppointmentDTO);
        boolean save = save(appointmentDetails);
        //添加药品
        appointmentDrugService.addAppointmentDrug(addAppointmentDTO, appointmentDetails.getId());
        if (!save) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请重新添加");
        }
        //添加webserver预约
//        this.addWebServerVO(addAppointmentDTO, appointmentDetails.getId());
        return true;
    }

    @Override
    public boolean updateAppointment(AppointmentDetails appointmentDetails) {
        return false;
    }

    /**
     * 取消预约
     */
    @Override
    @Transactional
    public boolean deleteAppointment(Long id) {
        //删除时更新号源状态,查询预约信息
        AppointmentDetails appointmentDetails = getById(id);
        if (appointmentDetails.getIsDelete() == 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请刷新重试");
        }
        //号源id
        String sourceId = appointmentDetails.getSourceId();
        String[] split = sourceId.split(",");
        //将id转换为集合
        List<Long> sourceIdList = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        Date appointDate = appointmentDetails.getAppointDate();
        //科室id
        Long infusionId = appointmentDetails.getInfusionId();
        //号源类型
        Integer sourceType = appointmentDetails.getSourceType();
        //预约时间
        String stringDate = DateUtil.format(appointDate, formatter);
        //组成添加号源条件
        DeductionSourceDTO deductionSourceDTO = new DeductionSourceDTO();
        deductionSourceDTO.setSourceId(sourceIdList);
        deductionSourceDTO.setDate(stringDate);
        deductionSourceDTO.setRoomId(infusionId);
        deductionSourceDTO.setSourceType(sourceType);
        boolean b = appointmentSourceService.addSource(deductionSourceDTO);
        if (!b) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "删除号源失败！请刷新重试");
        }
        //逻辑删除修改状态
        //删除登记预约
        RemoveVO removeVO = new RemoveVO();
        removeVO.setBlh(appointmentDetails.getCardNumber());
        removeVO.setYuyuehm(appointmentDetails.getQueueNumber());
        removeVO.setUserId(SecurityUtils.getUserId().toString());
        removeVO.setSys(appointmentDetails.getInfusionId().toString());
        this.cancelAppointment(removeVO);
        return removeById(id);
    }

    /**
     * 查询单个详情
     */
    @Override
    public AppointmentDetails getAppointmentById(Long id) {
        return getById(id);
    }

    /**
     * 构建pdh
     */
    @Override
    public String buildPdh(String data, List<String> aptTimeList) {
        String result = data.substring(5);
        LambdaQueryWrapper<AppointmentDetails> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AppointmentDetails::getAppointDate, data);
        lambdaQueryWrapper.eq(AppointmentDetails::getStatus, "0");
        List<AppointmentDetails> aptRes = baseMapper.selectList(lambdaQueryWrapper);
        String re;
        String startTime = aptTimeList.get(0);

        if (aptRes.isEmpty()) {
            String s1 = result + startTime + "01";
            re = s1.replace("-", "");
        } else {
            // 找到aptRes列表中序号最大的项
            int maxSequence = 0;
            for (AppointmentDetails ar : aptRes) {
                String sequenceStr = ar.getQueueNumber(); // 假设getSequence()方法获取的是包含序号的字符串
                int currentSequence = Integer.parseInt(sequenceStr.substring(sequenceStr.length() - 2));
                if (currentSequence > maxSequence) {
                    maxSequence = currentSequence;
                }
            }
            // 序号加1并补足两位
            maxSequence++;
            String nextSequenceStr = String.format("%02d", maxSequence);
            String s = data + startTime + nextSequenceStr;
            re = s.replace("-", "");
        }
        return re;
    }

    /**
     * 根据输液单获取药品详情
     */
    @Override
    public MainData getTransfusionInfo(String syd) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Web_GetCfxx xmlns=\"http://tempuri.org/\">\n" +
                "      <cfid>" + syd + "</cfid>\n" +
                "    </Web_GetCfxx>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        // 创建 HttpRequest 实例
        HttpRequest request = HttpRequest.post("http://192.168.63.6:" + host + "/EOIISService.asmx");
        // 设置请求头 Content-Type 为 application/xml
        request.header("Content-Type", "application/soap+xml");
        request.header(Header.ACCEPT_CHARSET, "utf-8");
        // 将 XML 字符串转换为 StringEntity，并设置到请求体中
        String body = request.body(xml).execute().body();
        Document docResult = XmlUtil.readXML(body);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        return gson.fromJson(value, MainData.class);
    }

    /**
     * 根据卡号开始时间和结束时间获取患者医嘱信息
     */
    @Override
    public List<MainData> getTransfusionInfoByCardNo(String cardNo) {
        //获取当前日期并转化为YYYY-mm-dd
        String end = DateUtil.format(new Date(), formatter);
        //结束时间是当前时间前面40天
        String start = DateUtil.format(DateUtil.offsetDay(new Date(), -40), formatter);

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Web_GetCflb xmlns=\"http://tempuri.org/\">\n" +
                "      <blh>" + cardNo + "</blh>\n" +
                "      <startime>" + start + "</startime>\n" +
                "      <endtime>" + end + "</endtime>\n" +
                "    </Web_GetCflb>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        // 创建 HttpRequest 实例
        HttpRequest request = HttpRequest.post("http://192.168.63.6:" + host + "/EOIISService.asmx");
        // 设置请求头 Content-Type 为 application/xml
        request.header("Content-Type", "application/soap+xml");
        request.header(Header.ACCEPT_CHARSET, "utf-8");
        //查询参数
        String body = request.body(xml).execute().body();
        Document docResult = XmlUtil.readXML(body);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        List<MainData> list = Arrays.asList(gson.fromJson(value, MainData[].class));
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "没有查询到药品！");
        }
        return list;
    }

    /**
     * 取消预约
     */
    @Override
    public boolean cancelAppointment(RemoveVO removeVO) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Web_CancelReservation xmlns=\"http://tempuri.org/\">\n" +
                "      <blh>" + removeVO.getBlh() + "</blh>\n" +
                "      <yuyuehm>" + removeVO.getYuyuehm() + "</yuyuehm>\n" +
                "      <jdtid>" + removeVO.getSys() + "</jdtid>\n" +
                "      <Operator>" + removeVO.getUserId() + "</Operator>\n" +
                "    </Web_CancelReservation>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        // 创建 HttpRequest 实例
        HttpRequest request = HttpRequest.post("http://192.168.63.6:" + host + "/EOIISService.asmx");
        // 设置请求头 Content-Type 为 application/xml
        request.header("Content-Type", "application/soap+xml");
        request.header(Header.ACCEPT_CHARSET, "utf-8");
        // 将 XML 字符串转换为 StringEntity，并设置到请求体中
        //查询参数
        String body = request.body(xml).execute().body();
        Document docResult = XmlUtil.readXML(body);
        String value = (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
        return value.equals("取消预约成功");
    }

    /**
     * 构建短信信息
     */
    @Override
    public String sendGetMessage(String date, String time, String site, String phone) {
        return "您已预约“" + DateUtil.year(new Date()) + "-" + date + " " + time + " 时" + "”输液" +
                "。请携带本人① 医保卡/就诊卡、② 病历本、③ 预约告知书、④电子输液单，至" + site + "完成到诊登记。" +
                "预约座位为您保留（30分钟），请准时到达！。如您不能按照预约日期前来输液，请于治疗当日07:00前（回复“取消预约”进行取消）。" +
                "咨询电话：" + phone + "（工作日8:00-16:00），谢谢配合！";
    }

    /**
     * 根据卡号获取输液信息
     */
    @Override
    public BaseInfo getPatientInfo(String cardNo) {
        //患者开始时间和结束时间的所有输液单号信息
        List<MainData> transfusionInfoByCardNo = getTransfusionInfoByCardNo(cardNo);
        //根据日期合并输液单号
        // 按照kfsj, RegisterID和Kfysxm进行分组
        Map<MainDataKey, List<MainData>> listMap = transfusionInfoByCardNo.stream().collect(Collectors.groupingBy(MainDataKey::new));
        ;
        //分组后查询药品
        List<TransfusionVO> list = new ArrayList<>();
        listMap.forEach((index, value) -> {
            TransfusionVO transfusionVO = new TransfusionVO();
            Set<String> sydlist = value.stream().map(MainData::getCfsbh).collect(Collectors.toSet());
            String jsonString = getJsonString(sydlist);
            MainData mainData = getTransfusionInfo(jsonString);
            transfusionVO.setYzxxsList(groupYzxxs(mainData.getYzxxs()));
            transfusionVO.setCreationTime(index.getKfsj());
            //已输液天数
            Integer infusion = isInfusion(mainData);
            Integer fysycs = getFysycs(mainData);
            Integer sycs = getsycs(mainData);
            transfusionVO.setInfusionNum(fysycs);
            //需要输液天数
            transfusionVO.setInfusionTotal(sycs);
            transfusionVO.setKfksmc(value.get(0).getKfksmc());
            transfusionVO.setInfusionTime(sunInfusionTime(mainData));
            //判断是否输液状态
            int status = 1;
            if (Objects.equals(fysycs, sycs)) {
                status = 0;
            } else if (fysycs == 0) {
                status = 2;
            }

            transfusionVO.setStatus(status);
            transfusionVO.setCreateDoctor(value.get(0).getKfysxm());
            transfusionVO.setIsCharge(isCharge(mainData.getYzxxs()));
            list.add(transfusionVO);
        });
        list.sort(Comparator.comparing(TransfusionVO::getCreationTime).reversed());
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setTransfusionVOList(list);
        baseInfo.setBrxx(transfusionInfoByCardNo.get(0).getBrxx());
        return baseInfo;
    }

    /**
     * 查询已有座位
     */
    @Override
    public List<AppointmentDetails> getAppointmentCount(DeductionSourceDTO appointmentDetails) {
        return baseMapper.getAppointmentCount(appointmentDetails);
    }

    /**
     * 扣减号源
     */
    private void dedSource(AddAppointmentDTO addAppointmentDTO) { //输液室id
        Long infusionId = addAppointmentDTO.getInfusionId();
        String appointDate = addAppointmentDTO.getAppointDate();
        List<Long> sourceId = addAppointmentDTO.getSourceId();
        Integer sourceType = addAppointmentDTO.getSourceType();
        //扣减号源
        DeductionSourceDTO deductionSourceDTO = new DeductionSourceDTO();
        deductionSourceDTO.setDate(appointDate);
        deductionSourceDTO.setRoomId(infusionId);
        deductionSourceDTO.setSourceId(sourceId);
        deductionSourceDTO.setSourceType(sourceType);
        appointmentSourceService.deductionSource(deductionSourceDTO);
    }

    /**
     * 构建webserver添加对象
     */
    private void addWebServerVO(AddAppointmentDTO addAppointmentDTO, Long id) {
        SaveYySydjVO saveYySydjVO = new SaveYySydjVO();
        //输液室信息
        Area area = new Area();
        area.setAreaName(addAppointmentDTO.getInfusionName());
        area.setId(addAppointmentDTO.getInfusionId());
        saveYySydjVO.setArea(area);
        //座位信息
        Bed bed = new Bed();
        bed.setId(addAppointmentDTO.getSeatId());
        bed.setBedName(addAppointmentDTO.getSeatNumber());
        saveYySydjVO.setBed(bed);
        //病人信息
        MedPatients patientsByCardNo = medPatientsService.getPatientsByCardNo(addAppointmentDTO.getCardNumber());
        Brxx brxx = new Brxx();
        brxx.convert(patientsByCardNo);
        saveYySydjVO.setBrxx(brxx);
        //处方信息列表
        //登记信息
        String staffCode = SecurityUtils.getUser().getStaffCode();
        TYhxx staffBaseinfo = tYhxxService.getOne(new LambdaQueryWrapper<TYhxx>().eq(TYhxx::getYhdm, staffCode));
        saveYySydjVO.setDjhs_gh(staffBaseinfo.getYhxm());
        saveYySydjVO.setDjhs_id(staffBaseinfo.getId());
        SysUser userName = SecurityUtils.getUser();
        saveYySydjVO.setYuyuehsxm(userName.getUserName());
        saveYySydjVO.setYuyuehs_id(userName.getUserId());
        String appointDate = addAppointmentDTO.getAppointDate();
        saveYySydjVO.setYuyuesj(appointDate);
        //放入syds
        //根据添加得药品信息输液单获取yzzxs
        String appointmentDrugByAppointmentId = appointmentDrugService.getAppointmentDrugByAppointmentId(id);
        //调用查询方法
        MainData transfusionInfo = this.getTransfusionInfo(appointmentDrugByAppointmentId);
        List<Yzxxs> yzxxs = transfusionInfo.getYzxxs();
        List<SydsVo> sYds = createSYds(yzxxs);
        saveYySydjVO.setSyds(sYds);
        saveYySydjVO.setYuyuehm(addAppointmentDTO.getQueueNumber());
        //预约时长
        List<String> aptTimeList = addAppointmentDTO.getAppointTime();
        String joinedString = String.join("-", aptTimeList);
        saveYySydjVO.setZwsc(joinedString);
        //处方信息
        List<MainData> transfusionInfoByCardNo = getTransfusionInfoByCardNo(addAppointmentDTO.getCardNumber());
        List<MainData> patient = getPatient(transfusionInfoByCardNo, yzxxs);
        saveYySydjVO.setCfxxlb(patient);
        //添加
        if (addAppointmentDTO.getSaveStatus() == 0) {
            saveYySydjVO.setYuyuehm("");
            saveYySydjVO.setYuyuesj("0001/1/1 0:00:00");
            saveYySydjVO.setYuyuehsxm("");
            saveYySydjVO.setYuyuehs_id(0L);
        }
        String json = gson.toJson(saveYySydjVO);
//        String s = addWebserviceYu(json);
        Long infusionId = addAppointmentDTO.getInfusionId();
        List<String> appointTime = addAppointmentDTO.getAppointTime();
        String s1 = appointTime.get(0);
        AppointmentRoom appointmentRoom = appointmentRoomService.getById(infusionId);
//        if (!s.equals("登记成功")) {
//            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加银江系统失败");
//        }
        if (addAppointmentDTO.getSaveStatus() != 0) {
            String message = sendGetMessage(appointDate, s1, appointmentRoom.getNumSite(), appointmentRoom.getPhone());
            //一共两条消息，一条预约成功消息
            DateUtils.sendMessage(appointmentRoom.getPhone(), message);
        }
    }

    private List<Yzxxs> groupYzxxs(List<Yzxxs> yzxxsList) {
        Map<Integer, List<Yzxxs>> yzxxsMap = yzxxsList.stream().collect(Collectors.groupingBy(Yzxxs::getZ_id));
        List<Yzxxs> updatedYzxxsList = new ArrayList<>();
        StringBuilder colorBuilder = new StringBuilder("1");

        yzxxsMap.forEach((zid, yzxxss) -> {
            // 设置第一个元素的 txbz 属性为0
            if (!yzxxss.isEmpty()) {
                yzxxss.get(0).setTxbz(0);
            }

            // 设置最后一个元素的 txbz 属性为2
            if (yzxxss.size() > 1) {
                yzxxss.get(yzxxss.size() - 1).setTxbz(2);
            }

            // 遍历列表，设置除了第一个和最后一个以外的所有元素的 txbz 属性为1
            for (int i = 1; i < yzxxss.size() - 1; i++) {
                yzxxss.get(i).setTxbz(1);
            }

            for (Yzxxs yzxxs : yzxxss) {
                yzxxs.setColor(colorBuilder.toString());
            }

            if (yzxxss.size() == 1) {
                yzxxss.get(0).setTxbz(3);
            }
            // 在添加到新列表前，先对每个组内的元素按 txbz 排序
            yzxxss.sort((a, b) -> {
                int aTxbz = a.getTxbz();
                int bTxbz = b.getTxbz();

                // 优先级：0 > 中间值 > 2
                if (aTxbz == 0) return -1;
                if (bTxbz == 0) return 1;
                if (aTxbz == 2) return 1;
                if (bTxbz == 2) return -1;

                // 如果两者都不是0也不是2，则正常比较
                return Integer.compare(aTxbz, bTxbz);
            });

            updatedYzxxsList.addAll(yzxxss);
            if (colorBuilder.toString().equals("1")) {
                colorBuilder.replace(0, 7, "0");
            } else {
                colorBuilder.replace(0, 7, "1");
            }

        });
        // 按照 z_id（组号）对整个列表排序
        updatedYzxxsList.sort(Comparator.comparing(Yzxxs::getZ_id));

        return updatedYzxxsList;
    }

    private static String getJsonString(Set<String> stringSet) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int count = 0;
        for (String str : stringSet) {
            sb.append("\"").append(str).append("\"");
            if (count < stringSet.size() - 1) {
                sb.append(",");
            }
            count++;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 查询是否输液
     */
    private Integer isInfusion(MainData mainData) {
        List<Yzxxs> yzxxs = mainData.getYzxxs();
        String syd = yzxxs.stream().map(Yzxxs::getSydh).collect(Collectors.joining("$"));
        List<AppointmentDrug> aptDrugs = appointmentDrugService.list(
                new LambdaQueryWrapper<AppointmentDrug>()
                        .eq(AppointmentDrug::getInfusionApplyNos, syd));
        return aptDrugs.size();
    }

    private Integer getFysycs(MainData mainData) {
        List<Yzxxs> yzxxs = mainData.getYzxxs();
        List<Integer> collect = yzxxs.stream().map(Yzxxs::getFysycs).collect(Collectors.toList());
        return collect.get(0);
    }

    private Integer getsycs(MainData mainData) {
        List<Yzxxs> yzxxs = mainData.getYzxxs();
        List<Integer> collect = yzxxs.stream().map(Yzxxs::getSycs).collect(Collectors.toList());
        return collect.get(0);
    }

    private Integer sunInfusionTime(MainData mainData) {
        List<Yzxxs> yzxxs = mainData.getYzxxs();
        List<String> integers = yzxxs.stream().filter(item -> item.getDsybz() == 1).map(Yzxxs::getSysc).collect(Collectors.toList());
        int sumTime = 0;
        for (String time : integers) {
            int i = Integer.parseInt(time);
            sumTime += i;
        }
        double totalHoursAsDouble = sumTime / (double) 60;
        return (int) Math.ceil(totalHoursAsDouble);
    }

    /**
     * 是否全部收费
     */
    private Boolean isCharge(List<Yzxxs> yzxxs) {
        return yzxxs.stream().map(Yzxxs::getSfzt).anyMatch("0"::equals);
    }

    /**
     * 构建处方信息
     */
    public List<MainData> getPatient(List<MainData> cfxx, List<Yzxxs> yzxx) {
        List<MainData> mainDataList = new ArrayList<>();
        for (MainData mainData : cfxx) {
            List<Yzxxs> yzxxsList = new ArrayList<>();
            for (Yzxxs yzxxs : yzxx) {
                if (mainData.getCfsbh().equals(yzxxs.getSydh())) {
                    yzxxsList.add(yzxxs);
                }
            }
            mainData.setYzxxs(yzxxsList);
            mainDataList.add(mainData);
        }
        return mainDataList;
    }

    public List<SydsVo> createSYds(List<Yzxxs> yzxx) {
        List<SydsVo> mainDataList = new ArrayList<>();
        Map<String, List<Yzxxs>> yzxxsMap = yzxx.stream().collect(Collectors.groupingBy(Yzxxs::getSydh));
        yzxxsMap.forEach((key, value) -> {
            SydsVo sydsVo = new SydsVo();
            sydsVo.setPs(value.get(0).getPs());
            sydsVo.setKbbz(value.get(0).getKbbz());
            List<Yzxxs> list = new ArrayList<>(value);
            sydsVo.setYzxxs(list);
            mainDataList.add(sydsVo);
        });
        return mainDataList;
    }

    /**
     * 登记输液单
     */
    private String addWebserviceYu(String s) {

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Web_SaveYySydj xmlns=\"http://tempuri.org/\">\n" +
                "      <sydj>" + s + "</sydj>\n" +
                "    </Web_SaveYySydj>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        // 创建 HttpRequest 实例
        HttpRequest request = HttpRequest.post("http://192.168.63.6:" + host + "/EOIISService.asmx");
        // 设置请求头 Content-Type 为 application/xml
        request.header("Content-Type", "application/soap+xml");
        request.header(Header.ACCEPT_CHARSET, "utf-8");
        // 将 XML 字符串转换为 StringEntity，并设置到请求体中
        //查询参数
        String body = request.body(xml).execute().body();
        Document docResult = XmlUtil.readXML(body);
        return (String) XmlUtil.getByXPath("//soap:Envelope/soap:Body", docResult, XPathConstants.STRING);
    }
}




