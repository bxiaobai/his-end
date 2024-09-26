package com.his.master.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;

import java.util.Date;

public class DateUtils {

    public static Integer getWeek(String week) {
        switch (week) {
            case "MONDAY":
                return 1;
            case "TUESDAY":
                return 2;
            case "WEDNESDAY":
                return 3;
            case "THURSDAY":
                return 4;
            case "FRIDAY":
                return 5;
            case "SATURDAY":
                return 6;
            default:
                return 7;
        }
    }

    /**
     * 短信发送工具类
     */
    public static String sendMessage(String phone, String content) {
        String url = "http://msg23.rjh.com.cn:8513/sms/Api/VarSend.do";
        String result = HttpRequest.get(url)
                .form("f", "1")
                .form("SpCode", "100001")
                .form("LoginName", "15710185979")
                .form("Password", "ad0854ccf109817a894dde63c6cedfde")
                .form("MessageContent", "{\"文本\":\"" + content + "\"}")
                .form("SerialNumber", DateUtil.format(new Date(), "yyyyMMddHHmmssHHmmss"))
                .form("UserNumber", phone)
                .form("templateId", "1100000018124")
                .execute()
                .body();
        if (result.contains("发送短信成功")){
            return "发送短信成功";
        }else {
            return "发送短信失败，请联系管理员";
        }
    }
}
