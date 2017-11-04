package com.piles.record.service;

import com.piles.record.entity.UploadChargeMonitorRequest;

/**
 * 上传充电过程监测数据接口
 */
public interface IUploadChargeMonitorService {
    /**
     * 上传充电过程监测数据
     *
     * @param uploadChargeMonitorRequest 请求体
     */
    void uploadChargeMonitor(UploadChargeMonitorRequest uploadChargeMonitorRequest);
}
