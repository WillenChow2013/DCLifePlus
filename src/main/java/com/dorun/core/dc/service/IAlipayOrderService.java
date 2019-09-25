package com.dorun.core.dc.service;

import com.dorun.core.dc.model.AlipayOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * undefined 服务类
 * </p>
 *
 * @author AleeX
 * @since 2019-09-20
 */
public interface IAlipayOrderService extends IService<AlipayOrder> {
    void generatorOrderFile();
}
