package com.piles.record.business.impl;


import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.record.entity.HeartBeatRequest;
import com.piles.record.entity.UploadRecordRequest;
import com.piles.record.service.IHeartBeatService;
import com.piles.record.service.IUploadRecordService;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传充电记录接口实现
 */
@Service("uploadRecordBusiness")
public class UploadRecordBusinessImpl extends BaseBusiness {

    @Resource
    private IUploadRecordService uploadRecordService;

    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");


    @Override
    protected byte[] processBody(byte[] bodyBytes,Channel incoming) {
        //依照报文体规则解析报文
        UploadRecordRequest uploadRecordRequest = UploadRecordRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag= uploadRecordService.uploadRecord(uploadRecordRequest);
        byte[] orderNo = BytesUtil.copyBytes(bodyBytes, 1, 8);
        byte[] result = flag==true?new byte[]{0x00}:new byte[]{0x01};
        byte[] responseBody = Bytes.concat(orderNo,result);
        //组装返回报文体
        return responseBody;
    }

    @Override
    public ECommandCode getReponseCode() {
        return ECommandCode.UPLOAD_RECORD_ANSWER_CODE;
    }
}
