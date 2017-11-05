package com.piles.control.service;

import com.piles.control.entity.RemoteClosePushRequest;
import com.piles.control.entity.RemoteStartPushRequest;

/**
 * 远程启动充电 运营中心主动调用服务
 */
public interface IRemoteStartPushService {
    /**
     * 远程启动充电 推送消息
     * @param remoteStartPushRequest
     * @return
     */
    boolean doPush(RemoteStartPushRequest remoteStartPushRequest);
}
