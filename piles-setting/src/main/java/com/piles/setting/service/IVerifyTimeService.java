package com.piles.setting.service;

import com.piles.setting.entity.VerifyTimeRequest;

/**
 * 校时
 */
public interface IVerifyTimeService {
    /**
     * 校时 推送
     * @param verifyTimeRequest 请求体
     * @return 验证成功返回true，失败返回false
     */
    boolean verify(VerifyTimeRequest verifyTimeRequest);
}
