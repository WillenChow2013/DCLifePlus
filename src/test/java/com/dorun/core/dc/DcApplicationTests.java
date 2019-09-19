package com.dorun.core.dc;

import com.dorun.core.dc.mapper.OrgInfoMapper;
import com.dorun.core.dc.model.OrgInfo;
import com.dorun.core.dc.service.ILifeOrderService;
import com.dorun.core.dc.utils.DESUtil;
import com.dorun.core.dc.utils.HttpUtil;
import com.dorun.core.dc.utils.TxtUtil;
import com.dorun.core.dc.yh.service.WebServiceServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DcApplicationTests {

    @Autowired
    private WebServiceServiceImpl webServiceServiceImpl;

    @Resource
    private TxtUtil txtUtil;

    @Value("${pay-type.wechat}")
    private String payType;

    @Autowired
    private ILifeOrderService lifeOrderService;

    @Test
    public void contextLoads() {
//        {"success":"100","userid":"01234567","username":"电子发票测试","useraddress":"江山如画9#楼1403-2","prestore":"0.00","amountsum":"2.76","lateamount":"0.00","monthcount":"1","YJamount":"2.76"}
//        System.out.println(webServiceServiceImpl.getUserInfo("02250214"));02250214
//        System.out.println(webServiceServiceImpl.pay("01234567","4200000333201905078482312157",payType,"3.00"));
//        System.out.println(txtUtil.creatTxtFile("test"));
        lifeOrderService.generatorOrderFile();
    }

}
