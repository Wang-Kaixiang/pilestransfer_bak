package com.piles.control.service;

import com.piles.control.entity.RemoteCloseRequest;

/**
 * 远程结束充电
 */
public interface IRemoteCloseService {
    /**
     * 远程结束充电
     * @param remoteCloseRequest 请求体
     * @return 验证成功返回true，失败返回false
     */
    boolean remoteClose(RemoteCloseRequest remoteCloseRequest);
}
