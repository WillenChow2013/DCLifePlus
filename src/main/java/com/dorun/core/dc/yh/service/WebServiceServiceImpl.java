package com.dorun.core.dc.yh.service;

import com.dorun.core.dc.utils.DESUtil;
import com.dorun.core.dc.utils.HttpUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebServiceServiceImpl {

    @Value("${web-service.target}")
    private String target;


    /**
     * 查询用户欠费信息
     * @param userKey
     * @return
     */
    public JSONObject getUserInfo(String userKey) {

        String encUserKey = DESUtil.encryptDES(userKey);

        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("Para00", encUserKey);

        String resultXml = HttpUtil.post(target + "/Web_QueryData", paramMap);

        Map<String, String> resultMap = HttpUtil.xmlToMap(resultXml);

        return JSONObject.fromObject(resultMap.get("result"));
    }

    /**
     * 发起缴费
     * @param userKey 客户编码
     * @param transactionNo 流水号
     * @param payType 缴费类型（微信发票，支付宝发票）
     * @param amount 缴费金额
     * @return
     */
    public String pay(String userKey,String transactionNo,String payType,String amount){

        Map<String,String> paramsMap = new HashMap<>();

        paramsMap.put("Para00",DESUtil.encryptDES(userKey));
        paramsMap.put("Para01",DESUtil.encryptDES(transactionNo));
        paramsMap.put("Para02",DESUtil.encryptDES(payType));
        paramsMap.put("Para03",DESUtil.encryptDES(amount));

        String resultXml = HttpUtil.post(target + "/Web_Pay",paramsMap);

        Map<String,String> resultMap = HttpUtil.xmlToMap(resultXml);

        JSONObject json = JSONObject.fromObject(resultMap.get("result"));

        return json.getString("success");
    }

}
