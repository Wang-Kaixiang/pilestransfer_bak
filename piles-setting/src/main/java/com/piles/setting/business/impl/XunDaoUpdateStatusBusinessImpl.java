package com.piles.setting.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.IBusiness;
import com.piles.common.entity.type.TradeType;
import com.piles.common.entity.type.XunDaoTypeCode;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.CRC16Util;
import com.piles.setting.domain.UpdateStatusReport;
import com.piles.setting.entity.XunDaoUpdateStatusRequest;
import com.piles.setting.service.IUpdateStatusService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 循道升级状态汇报 充电桩  运营管理系统
 */
@Slf4j
@Service("xunDaoUpdateStatusBusiness")
public class XunDaoUpdateStatusBusinessImpl implements IBusiness {


    @Resource
    private IUpdateStatusService updateStatusService;

    //返回报文记录类型
    private int recordType = 53;
    @Override
    public byte[] process(byte[] msg, Channel incoming) {

        log.info( "接收到循道充电桩升级结果汇报报文" );
        byte[] dataBytes = BytesUtil.copyBytes(msg, 13, (msg.length - 13));

        //依照报文体规则解析报文
        XunDaoUpdateStatusRequest updatePackageRequest = XunDaoUpdateStatusRequest.packEntity(dataBytes, incoming);
        log.info( "接收到循道充电桩升级结果汇报报文:{}", updatePackageRequest.toString() );
        UpdateStatusReport uploadRecord = buildServiceEntity(updatePackageRequest);

        updateStatusService.updateStatus(uploadRecord);
        //组装返回报文体
        byte[] response = buildReponse(msg);
        return response;
    }

    //封装返回报文
    private byte[] buildReponse(byte[] msg) {
        //消息头
        byte[] bytes = BytesUtil.copyBytes(msg, 0, 13);
        //设置类型标识
        BytesUtil.replaceBytes(bytes,6,BytesUtil.intToBytesLittle(XunDaoTypeCode.SEND_DATA_CODE.getCode(),1));
        //替换记录类型
        BytesUtil.replaceBytes(bytes,12,BytesUtil.intToBytesLittle(recordType,1));

        //终端机器编码
        byte[] dataBytes = BytesUtil.copyBytes(msg, 13, 8);
        Bytes.concat(bytes,dataBytes);
        byte[] xunDaoCRC = CRC16Util.getXunDaoCRC(dataBytes);
        //替换crc
        BytesUtil.replaceBytes(bytes,10,xunDaoCRC);
        //替换长度
        BytesUtil.replaceBytes(bytes,1,BytesUtil.intToBytesLittle(bytes.length-2));
        return bytes;
    }

    private UpdateStatusReport buildServiceEntity(XunDaoUpdateStatusRequest updatePackageRequest) {
        UpdateStatusReport updateStatusReport = new UpdateStatusReport();
        updateStatusReport.setTradeTypeCode(TradeType.XUN_DAO.getCode());
        updateStatusReport.setPileNo(updatePackageRequest.getPileNo());
        updateStatusReport.setProtocolVersion(updatePackageRequest.getSoftVersion());
        updateStatusReport.setStatus(updatePackageRequest.getStatus());
//        updateStatusReport.setProtocolVersion();
        return updateStatusReport;
    }
}
