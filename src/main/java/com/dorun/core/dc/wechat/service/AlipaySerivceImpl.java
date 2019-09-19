package com.dorun.core.dc.wechat.service;

import com.dorun.core.dc.yh.service.WebServiceServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipaySerivceImpl {
    @Autowired
    private WebServiceServiceImpl webServiceServiceImpl;

    public Map<String, String> queryConsInfo(String consNo) {
        JSONObject consJson = webServiceServiceImpl.getUserInfo(consNo);

        Map<String, String> result = new HashMap<>();

        if(!"100".equals(consJson.getString("success")))
            return null;



        result.put("addr", consJson.getString("useraddress"));
        result.put("consNo", consJson.getString("userid"));
        result.put("consName",consJson.getString("username"));
        result.put("rcvblYm", "");
        result.put("tPq","0");
        result.put("rcvblAmt", consJson.getString("amountsum"));//水费合计（不包含违约金）
        result.put("rcvedAmt",consJson.getString("YJamount"));
        result.put("prepayAmt", consJson.getString("prestore"));//余额
        result.put("rcvblPenalty",consJson.getString("lateamount"));//违约金
        //欠费金额 = 每期消费合计 + 违约金
        result.put("oweAmt",new BigDecimal(consJson.getString("amountsum")).add(new BigDecimal(consJson.getString("lateamount"))).setScale(2,BigDecimal.ROUND_HALF_UP).toString());

        return result;
    }

    public String booking(String key, String rcvedAmt, String channelBillNo, String payTime) {

        JSONObject consJson = webServiceServiceImpl.getUserInfo(key);

        String recvebleAmout = consJson.getString("YJamount");

        if(new BigDecimal(rcvedAmt).subtract(new BigDecimal(recvebleAmout)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() < 0)
            return "error";

        return webServiceServiceImpl.pay(key,channelBillNo,"支付宝发票",rcvedAmt);
    }
}
