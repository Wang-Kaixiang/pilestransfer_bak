package com.piles.record.business.impl;


import com.piles.common.business.BaseBusiness;
import com.piles.record.entity.UploadChargeMonitorRequest;
import com.piles.record.service.IUploadChargeMonitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 上传充电过程监测数据 接口实现
 */
@Service("uploadChargeMonitorBusiness")
public class UploadChargeMonitorBusinessImpl extends BaseBusiness {

    @Resource
    private IUploadChargeMonitorService uploadChargeMonitorService;


    @Override
    protected byte[] processBody(byte[] bodyBytes) {
        //依照报文体规则解析报文
        UploadChargeMonitorRequest uploadChargeMonitorRequest = UploadChargeMonitorRequest.packEntity(bodyBytes);
        //调用底层接口
        uploadChargeMonitorService.uploadChargeMonitor(uploadChargeMonitorRequest);
        //组装返回报文体
        return null;
    }
}
