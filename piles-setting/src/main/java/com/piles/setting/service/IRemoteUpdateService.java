package com.piles.setting.service;

import com.piles.setting.entity.RemoteUpdateRequest;

/**
 * 远程升级
 */
public interface IRemoteUpdateService {
    /**
     * 远程升级
     * @param remoteUpdateRequest 请求体
     * @return 验证成功返回true，失败返回false
     */
    boolean remoteUpdate(RemoteUpdateRequest remoteUpdateRequest);
}
