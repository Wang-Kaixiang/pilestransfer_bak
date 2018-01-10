package com.piles.record.business.impl;


import com.google.common.primitives.Bytes;
import com.piles.common.business.IBusiness;
import com.piles.common.entity.type.TradeType;
import com.piles.common.entity.type.XunDaoTypeCode;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.CRC16Util;
import com.piles.record.domain.UploadRecord;
import com.piles.record.entity.XunDaoUploadRecordRequest;
import com.piles.record.service.IUploadRecordService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 上传充电记录接口实现
 */
@Slf4j
@Service("xunDaoUploadRecordBusiness")
public class XunDaoUploadRecordBusinessImpl implements IBusiness {

    @Resource
    private IUploadRecordService uploadRecordService;


    //返回报文记录类型
    private int recordType = 15;

    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        log.info( "接收到循道充电桩上传充电记录报文" );
        //依照报文体规则解析报文
        byte[] dataBytes = BytesUtil.copyBytes(msg, 13, (msg.length - 13));
        XunDaoUploadRecordRequest uploadRecordRequest = XunDaoUploadRecordRequest.packEntity( dataBytes );
//        uploadRecordRequest.setPileNo( ChannelMapByEntity.getChannel( incoming ).getPileNo() );
//        uploadRecordRequest.setSerial(order);
        log.info( "接收到循道充电桩上传充电记录报文:{}", uploadRecordRequest.toString() );
        UploadRecord uploadRecord = buildServiceEntity(uploadRecordRequest);
        //添加serial
        int serial = BytesUtil.xundaoControlByte2Int(BytesUtil.copyBytes(msg, 2, 4));
        uploadRecord.setSerial(serial);
        //调用底层接口
        boolean flag = uploadRecordService.uploadRecord( uploadRecord );
//        byte[] orderNo = BytesUtil.copyBytes( bodyBytes, 1, 8 );
//        byte[] responseBody = Bytes.concat( orderNo, result );
        return buildReponse(msg,flag);
    }

    //封装返回报文
    private byte[] buildReponse(byte[] msg, boolean result) {
        //消息头
        byte[] bytes = BytesUtil.copyBytes(msg, 0, 13);
        //设置类型标识
        BytesUtil.replaceBytes(bytes,6,BytesUtil.intToBytesLittle(XunDaoTypeCode.SEND_DATA_CODE.getCode(),1));
        //替换记录类型
        BytesUtil.replaceBytes(bytes,12,BytesUtil.intToBytesLittle(recordType,1));

        // TODO 失败直接返回的3 0:成功，1:记录格式有误失 败，2:数据重复;3:交易记 录插入数据库失败
        byte[] resultByte = result == true ? new byte[]{0x00} : new byte[]{0x03};
        //加上终端机器编码
        byte[] dataBytes = Bytes.concat(BytesUtil.copyBytes(msg, 13, 8), resultByte);
        Bytes.concat(bytes,dataBytes);
        byte[] xunDaoCRC = CRC16Util.getXunDaoCRC(dataBytes);
        //替换crc
        BytesUtil.replaceBytes(bytes,10,xunDaoCRC);
        //替换长度
        BytesUtil.replaceBytes(bytes,1,BytesUtil.intToBytesLittle(bytes.length-2));
        return bytes;
    }

    private UploadRecord buildServiceEntity(XunDaoUploadRecordRequest uploadRecordRequest){
        UploadRecord uploadRecord = new UploadRecord();
        uploadRecord.setTradeTypeCode(TradeType.XUN_DAO.getCode());
        uploadRecord.setOrderNo(uploadRecordRequest.getOrderNo());
        uploadRecord.setPileNo(uploadRecordRequest.getPileNo());

        //TODO  Serial
//        uploadRecord.setSerial(uploadRecordRequest.getOrderNo());
        //TODO 错误编码能匹配上吗
        uploadRecord.setEndReason(uploadRecordRequest.getStopChargeReason());
        uploadRecord.setTotalAmmeterDegree(uploadRecordRequest.getTotalAmmeterDegree());
        return uploadRecord;
    }
}
