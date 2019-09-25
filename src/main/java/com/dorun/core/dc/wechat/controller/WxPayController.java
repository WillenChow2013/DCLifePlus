package com.dorun.core.dc.wechat.controller;


import com.dorun.core.dc.utils.Result;
import com.dorun.core.dc.wechat.annotation.SerializedField;
import com.dorun.core.dc.wechat.service.PaymentServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信生活缴费接口
 * @author AleeX
 * @dete 2019年9月4日 23点49分
 */
@Controller
@RequestMapping("/api/cons")
public class WxPayController {

    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    /**
     * 查询用户
     * @param requestBody
     * @return
     */
    @SerializedField
    @ResponseBody
    @PostMapping("/query")
    public Result queryCons(@RequestBody String requestBody) {
        try {
            return Result.ok(paymentServiceImpl.queryCons(JSONObject.fromObject(requestBody).getString("consNo")));
        } catch (Exception e) {
            return Result.ok(0);
        }
    }

    /**
     * 查询用户详情
     * @param requestBody
     * @return
     */
    @SerializedField
    @ResponseBody
    @PostMapping("/details")
    public Result queryDetails(@RequestBody String requestBody) {

        try {
            return Result.ok(paymentServiceImpl.queryDetails(JSONObject.fromObject(requestBody).getString("consNo")));
        } catch (Exception e) {
            return Result.errorMsg(e.getMessage());
        }

    }

    /**
     * 销账
     * @param requestBody
     * @return
     */
    @SerializedField
    @ResponseBody
    @PostMapping("/booking")
    public Result booking(@RequestBody String requestBody) {

        try {
            return Result.ok(paymentServiceImpl.booking(JSONObject.fromObject(requestBody).getString("key"),
                    JSONObject.fromObject(requestBody).getString("rcvedAmt"),
                    JSONObject.fromObject(requestBody).get("channelBillNo").toString(), (JSONObject.fromObject(requestBody).get("payTime").toString()).split("\\.")[0]));
        } catch (Exception e) {

            return Result.errorMsg(e.getMessage());
        }

    }

}
