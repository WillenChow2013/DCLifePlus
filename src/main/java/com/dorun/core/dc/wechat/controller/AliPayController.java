package com.dorun.core.dc.wechat.controller;

import com.dorun.core.dc.utils.Result;
import com.dorun.core.dc.wechat.annotation.SerializedField;
import com.dorun.core.dc.wechat.service.AlipaySerivceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/ali/pay")
public class AliPayController {

    @Autowired
    private AlipaySerivceImpl alipaySerivce;

    @SerializedField
    @ResponseBody
    @PostMapping("/query")
    public Result queryCons(@RequestBody String requestBody) {
        try {
            return Result.ok(alipaySerivce.queryConsInfo(JSONObject.fromObject(requestBody).getString("consNo")));
        } catch (Exception e) {
            return Result.errorException("查询失败");
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
            return Result.ok(alipaySerivce.booking(JSONObject.fromObject(requestBody).getString("consNo"),
                    JSONObject.fromObject(requestBody).getString("amount"),
                    JSONObject.fromObject(requestBody).get("bankSerial").toString(), (JSONObject.fromObject(requestBody).get("payDateTime").toString()).split("\\.")[0]));
        } catch (Exception e) {
            return Result.errorMsg(e.getMessage());
        }

    }

}
