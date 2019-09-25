package com.dorun.core.dc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dorun.core.dc.model.AlipayOrder;
import com.dorun.core.dc.mapper.AlipayOrderMapper;
import com.dorun.core.dc.service.IAlipayOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dorun.core.dc.utils.FTPTool;
import com.dorun.core.dc.utils.Tools;
import com.dorun.core.dc.utils.TxtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * undefined 服务实现类
 * </p>
 *
 * @author AleeX
 * @since 2019-09-20
 */
@Service
public class AlipayOrderServiceImpl extends ServiceImpl<AlipayOrderMapper, AlipayOrder> implements IAlipayOrderService {

    @Resource
    private TxtUtil txtUtil;

    @Value("${txt.path}")
    private String path;

    @Value("${ftp.alipay.username}")
    private String userName;

    @Value("${ftp.alipay.password}")
    private String password;

    @Autowired
    private FTPTool ftpTool;

    @Async
    @Override
    public void generatorOrderFile(String date) {

        QueryWrapper<AlipayOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id","1");
        List<String>  datePast= Tools.getPastDates(1);
        if(StringUtils.isBlank(date))
            date = Tools.getPastDate(1);
        queryWrapper.between("bank_date_time", date + " 00:00:00",date + " 23:59:59");
        queryWrapper.orderByDesc("bank_date_time");

        List<AlipayOrder> alipayOrderList = this.baseMapper.selectList(queryWrapper);

        String fileName = "/" +  "ZLS17" + date.replaceAll("-","");

        txtUtil.creatTxtFile(fileName);

        for(AlipayOrder ao : alipayOrderList){
            String ctx = ao.getBankSerial() + "|" + ao.getConsNo() + "|"  +  ao.getBankDateTime().split((" "))[0].replaceAll("-","") + "|" + ao.getRcvedAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "|";
            txtUtil.writeTxtFile(ctx, fileName);
        }

        ftpTool.uploadFIle(path,fileName + ".txt","",userName,password);

    }
}
