package com.dorun.core.dc.service;

import com.dorun.core.dc.model.LifeOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  微信生活支付账单服务类
 * </p>
 *
 * @author AleeX
 * @since 2019-09-05
 */
public interface ILifeOrderService extends IService<LifeOrder> {

    void generatorOrderFile(String date);

}
