package com.piles.setting.service;

import com.piles.setting.entity.RebootRequest;

/**
 * 重启
 */
public interface IRebootService {
    /**
     * 重启
     * @param rebootRequest 请求体
     * @return 验证成功返回true，失败返回false
     */
    boolean reboot(RebootRequest rebootRequest);
}
