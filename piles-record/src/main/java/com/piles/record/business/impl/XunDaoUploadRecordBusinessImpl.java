package com.piles.record.business.impl;


import com.piles.common.business.IBusiness;
import com.piles.record.entity.XunDaoUploadRecordRequest;
import com.piles.record.service.IUploadRecordService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * 上传充电记录接口实现
 */
@Slf4j
@Service("xunDaoUploadRecordBusiness")
public class XunDaoUploadRecordBusinessImpl implements IBusiness {

    @Resource
    private IUploadRecordService uploadRecordService;

    private static SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmss" );


//    @Override
//    protected byte[] processBody(byte[] bodyBytes, Channel incoming, int order) {
//
//    }
//
//    @Override
//    public ECommandCode getReponseCode() {
//        return ECommandCode.UPLOAD_RECORD_ANSWER_CODE;
//    }

    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        log.info( "接收到循道充电桩上传充电记录报文" );
        //依照报文体规则解析报文
        XunDaoUploadRecordRequest uploadRecordRequest = XunDaoUploadRecordRequest.packEntity( msg );
//        uploadRecordRequest.setPileNo( ChannelMapByEntity.getChannel( incoming ).getPileNo() );
////        uploadRecordRequest.setSerial(order);
//        log.info( "接收到循道充电桩上传充电记录报文:{}", uploadRecordRequest.toString() );
//        //调用底层接口
//        boolean flag = uploadRecordService.uploadRecord( uploadRecordRequest );
//        byte[] orderNo = BytesUtil.copyBytes( bodyBytes, 1, 8 );
//        byte[] result = flag == true ? new byte[]{0x00} : new byte[]{0x01};
//        byte[] responseBody = Bytes.concat( orderNo, result );
//        //组装返回报文体
//        return responseBody;
        return null;
    }
}
