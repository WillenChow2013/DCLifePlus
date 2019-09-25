package com.dorun.core.dc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dorun.core.dc.mapper.OrgInfoMapper;
import com.dorun.core.dc.model.LifeOrder;
import com.dorun.core.dc.mapper.LifeOrderMapper;
import com.dorun.core.dc.model.OrgInfo;
import com.dorun.core.dc.service.ILifeOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dorun.core.dc.utils.FTPTool;
import com.dorun.core.dc.utils.SFTPTool;
import com.dorun.core.dc.utils.Tools;
import com.dorun.core.dc.utils.TxtUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 微信生活缴费服务实现类
 * </p>
 *
 * @author AleeX
 * @since 2019-09-05
 */
@Service
public class LifeOrderServiceImpl extends ServiceImpl<LifeOrderMapper, LifeOrder> implements ILifeOrderService {

    @Autowired
    private OrgInfoMapper orgInfoMapper;

    @Autowired
    private LifeOrderMapper lifeOrderMapper;

    @Resource
    private TxtUtil txtUtil;

    @Value("${txt.path}")
    private String path;

    @Value("${ftp.wx.username}")
    private String userName;

    @Value("${ftp.wx.password}")
    private String password;

    @Autowired
    private FTPTool ftpTool;

    @Async
    @Override
    public void generatorOrderFile(String date) {

        QueryWrapper<OrgInfo> orgInfoQueryWrapper = new QueryWrapper<>();
        orgInfoQueryWrapper.eq("id", "190422BPK95P0568");

        List<OrgInfo> orgInfoList = orgInfoMapper.selectList(orgInfoQueryWrapper);

        if(StringUtils.isBlank(date))
            date = Tools.getPastDate(1);

        for (OrgInfo oi : orgInfoList) {
            QueryWrapper<LifeOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("org_id", oi.getOrgId());
            queryWrapper.between("pay_time", date + " 00:00:00", date + " 23:59:59");
            List<LifeOrder> lifeOrderList = lifeOrderMapper.selectList(queryWrapper);

            String fileName = "/" + oi.getOrgId() + "/" + "ZLS16" + date.replaceAll("-", "");

            txtUtil.creatTxtFile(fileName);


            for (LifeOrder lo : lifeOrderList) {
                String ctx = lo.getTransactionId() + "|" + lo.getConsNo() + "|" + Tools.toDateString(lo.getPayTime()).split((" "))[0].replaceAll("-", "") + "|" + lo.getRcvedAmt().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "|";
                txtUtil.writeTxtFile(ctx, fileName);

            }

            ftpTool.uploadFIle(path,fileName + ".txt","",userName,password);
        }

    }

}
