package com.dorun.core.dc.schedule;

import com.dorun.core.dc.service.IAlipayOrderService;
import com.dorun.core.dc.service.ILifeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class StaticScheduleTask {

    @Autowired
    private IAlipayOrderService alipayOrderService;

    @Autowired
    private ILifeOrderService lifeOrderService;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendWxLifeOrder(){
        lifeOrderService.generatorOrderFile(null);
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendAliOrder(){
        alipayOrderService.generatorOrderFile(null);
    }

}
