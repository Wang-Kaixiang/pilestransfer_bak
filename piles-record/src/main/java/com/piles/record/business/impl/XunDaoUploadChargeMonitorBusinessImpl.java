package com.piles.record.business.impl;


import com.piles.common.business.IBusiness;
import com.piles.common.entity.ChannelEntity;
import com.piles.common.entity.type.TradeType;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.ChannelMapByEntity;
import com.piles.record.domain.UploadChargeMonitor;
import com.piles.record.entity.XunDaoUploadChargeMonitorRequest;
import com.piles.record.service.IUploadChargeMonitorService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 上传充电过程监测数据 接口实现
 */
@Slf4j
@Service("xunDaoUploadChargeMonitorBusiness")
public class XunDaoUploadChargeMonitorBusinessImpl implements IBusiness {

    @Resource
    private IUploadChargeMonitorService uploadChargeMonitorService;


    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        log.info("接收到循道充电桩上传充电过程监测数据报文");
        byte[] dataBytes = BytesUtil.copyBytes(msg, 13, (msg.length - 13));

        //依照报文体规则解析报文
        XunDaoUploadChargeMonitorRequest uploadChargeMonitorRequest = XunDaoUploadChargeMonitorRequest.packEntity(dataBytes);
        log.info("接收到循道充电桩上传充电过程监测数据报文:{}", uploadChargeMonitorRequest.toString());
        ChannelEntity channel = ChannelMapByEntity.getChannel(incoming);
        if (null == channel) {
            ChannelEntity channelEntity = new ChannelEntity(uploadChargeMonitorRequest.getPileNo(), TradeType.fromCode(TradeType.XUN_DAO.getCode()));
            ChannelMapByEntity.addChannel(channelEntity, incoming);
            ChannelMapByEntity.addChannel(incoming, channelEntity);
        }
        UploadChargeMonitor uploadChargeMonitor = buildServiceEntity(uploadChargeMonitorRequest);
        //调用底层接口
        uploadChargeMonitorService.uploadChargeMonitor(uploadChargeMonitor);
        //组装返回报文体
        return null;
    }

    private UploadChargeMonitor buildServiceEntity(XunDaoUploadChargeMonitorRequest uploadChargeMonitorRequest) {
        UploadChargeMonitor updateStatusReport = new UploadChargeMonitor();
        updateStatusReport.setTradeTypeCode(TradeType.XUN_DAO.getCode());
        updateStatusReport.setPileNo(uploadChargeMonitorRequest.getPileNo());
        //TODO 封装实体
//        updateStatusReport.setProtocolVersion();
        return updateStatusReport;
    }


}
