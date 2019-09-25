package com.dorun.core.dc.controller;


import com.dorun.core.dc.service.IAlipayOrderService;
import com.dorun.core.dc.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * undefined 前端控制器
 * </p>
 *
 * @author AleeX
 * @since 2019-09-20
 */
@RestController
@RequestMapping("/dc/alipayOrder")
public class AlipayOrderController {

    @Autowired
    private IAlipayOrderService alipayOrderService;

    @PostMapping("/file")
    public Result genFile(String date){
        try{
            alipayOrderService.generatorOrderFile(date);
            return Result.ok();
        }catch (Exception e){
            return Result.errorException("对账单发送失败！");
        }
    }

}
