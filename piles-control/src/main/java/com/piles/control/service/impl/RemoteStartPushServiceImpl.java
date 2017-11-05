package com.piles.control.service.impl;

import com.piles.common.business.IPushBusiness;
import com.piles.control.entity.RemoteClosePushRequest;
import com.piles.control.entity.RemoteStartPushRequest;
import com.piles.control.service.IRemoteClosePushService;
import com.piles.control.service.IRemoteStartPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 远程开始充电给充电桩发送消息实现类
 */
@Service
public class RemoteStartPushServiceImpl implements IRemoteStartPushService {
    @Autowired
    IPushBusiness pushBusiness;
    @Override
    public boolean doPush(RemoteStartPushRequest remoteStartPushRequest) {
        byte[] pushMsg=RemoteStartPushRequest.packBytes(remoteStartPushRequest);
        return pushBusiness.push(pushMsg,remoteStartPushRequest.getPileNo());
    }
}
