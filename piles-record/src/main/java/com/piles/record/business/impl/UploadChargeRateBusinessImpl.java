package com.piles.record.business.impl;


import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.record.entity.UploadChargeMonitorRequest;
import com.piles.record.entity.UploadChargeRateRequest;
import com.piles.record.entity.UploadRecordRequest;
import com.piles.record.service.IUploadChargeRateService;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 上传充电进度接口实现
 */
@Service("uploadChargeRateBusiness")
public class UploadChargeRateBusinessImpl extends BaseBusiness {

    @Resource
    private IUploadChargeRateService uploadChargeRateService;


    @Override
    protected byte[] processBody(byte[] bodyBytes,Channel incoming,int order) {
        //依照报文体规则解析报文
        UploadChargeRateRequest uploadChargeRateRequest = UploadChargeRateRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = uploadChargeRateService.uploadChargeRate(uploadChargeRateRequest);
//        byte[] orderNo = BytesUtil.copyBytes(bodyBytes, 1, 8);
//        byte[] result = flag==true?new byte[]{0x00}:new byte[]{0x01};
//        byte[] responseBody = Bytes.concat(orderNo,result);
        //组装返回报文体
        return null;
    }

    @Override
    public ECommandCode getReponseCode() {
        return null;
    }
}
