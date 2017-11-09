package com.piles.record.business.impl;


import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.ChannelMap;
import com.piles.record.entity.HeartBeatRequest;
import com.piles.record.entity.UploadChargeMonitorRequest;
import com.piles.record.service.IUploadChargeMonitorService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 上传充电过程监测数据 接口实现
 */
@Slf4j
@Service("uploadChargeMonitorBusiness")
public class UploadChargeMonitorBusinessImpl extends BaseBusiness {

    @Resource
    private IUploadChargeMonitorService uploadChargeMonitorService;


    @Override
    protected byte[] processBody(byte[] bodyBytes, Channel incoming, int order) {
        log.info("接收到充电桩上传充电过程监测数据报文");
        //依照报文体规则解析报文
        UploadChargeMonitorRequest uploadChargeMonitorRequest = UploadChargeMonitorRequest.packEntity(bodyBytes);
        uploadChargeMonitorRequest.setPileNo( ChannelMap.getChannel( incoming ) );
        log.info("接收到充电桩上传充电过程监测数据报文:{}", uploadChargeMonitorRequest.toString());
        //调用底层接口
        uploadChargeMonitorService.uploadChargeMonitor(uploadChargeMonitorRequest);
        System.out.println(uploadChargeMonitorRequest);
        //组装返回报文体
        return null;
    }

    @Override
    public ECommandCode getReponseCode() {
        return null;
    }
}
