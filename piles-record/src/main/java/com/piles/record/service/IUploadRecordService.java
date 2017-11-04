package com.piles.record.service;

import com.piles.record.entity.UploadRecordRequest;

/**
 * 上传充电记录接口
 */
public interface IUploadRecordService {
    /**
     * 上传充电记录
     * @param uploadRecordRequest 请求体
     * @return 成功返回boolean类型
     */
    boolean uploadRecord(UploadRecordRequest uploadRecordRequest);
}
