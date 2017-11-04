package com.piles.control.service;

import com.piles.control.entity.RemoteStartRequest;

/**
 * 远程启动充电
 */
public interface IRemoteStartService {
    /**
     * 远程启动充电
     * @param remoteStartRequest 请求体
     * @return 验证成功返回true，失败返回false
     */
    boolean remoteStart(RemoteStartRequest remoteStartRequest);
}
