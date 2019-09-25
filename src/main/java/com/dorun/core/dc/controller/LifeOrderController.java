package com.dorun.core.dc.controller;


import com.dorun.core.dc.service.ILifeOrderService;
import com.dorun.core.dc.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AleeX
 * @since 2019-09-05
 */
@RestController
@RequestMapping("/dc/lifeOrder")
public class LifeOrderController {

    @Autowired
    private ILifeOrderService lifeOrderService;

    @PostMapping("/file")
    public Result genFile(String date){
        try{
            lifeOrderService.generatorOrderFile(date);
            return Result.ok();
        }catch (Exception e){
            return Result.errorException("对账单发送失败！");
        }
    }

}
