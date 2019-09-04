package com.dorun.core.dc;

import com.dorun.core.dc.utils.DESUtil;
import com.dorun.core.dc.utils.HttpUtil;
import com.dorun.core.dc.yh.service.WebServiceServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DcApplicationTests {

    @Autowired
    private WebServiceServiceImpl webServiceServiceImpl;

    @Test
    public void contextLoads() {

        System.out.println(webServiceServiceImpl.getUserInfo("01234567"));
//        System.out.println(webServiceServiceImpl.pay("01234567","4200000333201905078482312056","微信发票","1.00"));
    }

}
