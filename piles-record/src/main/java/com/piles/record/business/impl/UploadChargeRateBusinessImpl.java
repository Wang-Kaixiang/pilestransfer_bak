package com.piles.record.business.impl;


import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.ChannelMap;
import com.piles.record.entity.UploadChargeRateRequest;
import com.piles.record.service.IUploadChargeRateService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 上传充电进度接口实现
 */
@Slf4j
@Service("uploadChargeRateBusiness")
public class UploadChargeRateBusinessImpl extends BaseBusiness {

    @Resource
    private IUploadChargeRateService uploadChargeRateService;


    @Override
    protected byte[] processBody(byte[] bodyBytes, Channel incoming, int order) {
        log.info( "接收到充电桩上传充电进度报文" );
        //依照报文体规则解析报文
        UploadChargeRateRequest uploadChargeRateRequest = UploadChargeRateRequest.packEntity( bodyBytes );
        uploadChargeRateRequest.setPileNo( ChannelMap.getChannel( incoming ) );
        log.info( "接收到充电桩上传充电进度报文:{}", uploadChargeRateRequest.toString() );
        //调用底层接口
        boolean flag = uploadChargeRateService.uploadChargeRate( uploadChargeRateRequest );
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
