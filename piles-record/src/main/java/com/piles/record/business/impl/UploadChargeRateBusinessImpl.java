package com.piles.record.business.impl;


import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
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
    protected byte[] processBody(byte[] bodyBytes,Channel incoming) {
        //依照报文体规则解析报文
        UploadChargeRateRequest uploadChargeRateRequest = UploadChargeRateRequest.packEntity(bodyBytes);
        //调用底层接口
        uploadChargeRateService.uploadChargeRate(uploadChargeRateRequest);
        //组装返回报文体
        return null;
    }

    @Override
    public ECommandCode getReponseCode() {
        //TODO 设置返回编码
        return null;
    }
}
