package com.dorun.core.dc.wechat.service;

import com.dorun.core.dc.yh.service.WebServiceServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl {

    @Value("${pay-type.wechat}")
    private String payType;

    @Autowired
    private WebServiceServiceImpl webServiceServiceImpl;

    public  int queryCons(String consNo){
        JSONObject consJson = webServiceServiceImpl.getUserInfo(consNo);

        if ("100".equals(consJson.getString("success")))
            return 1;
        else
            return 0;
    }

    public Map<String, Object> queryDetails(String consNo){
        JSONObject consJson = webServiceServiceImpl.getUserInfo(consNo);

        Map<String, Object> result = new HashMap<>();

        result.put("khmc",consJson.getString("username"));

        result.put("yfk", consJson.getString("prestore"));
        result.put("zqf", consJson.getString("YJamount"));
        result.put("sfzq", null);
        result.put("yhdz", consJson.getString("useraddress"));
        result.put("khbm", consJson.getString("userid"));
        result.put("sbbm", "01");
        result.put("qfsl","0");
        result.put("bmbm", "1");

        return result;

    }

    public String booking(String key, String rcvedAmt, String channelBillNo, String payTime) {

        JSONObject consJson = webServiceServiceImpl.getUserInfo(key);

        String recvebleAmout = consJson.getString("YJamount");

        if(new BigDecimal(rcvedAmt).subtract(new BigDecimal(recvebleAmout)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() < 0)
            return "error";

        return webServiceServiceImpl.pay(key,channelBillNo,payType,rcvedAmt);
    }

}
