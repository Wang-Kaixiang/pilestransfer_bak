package com.piles.control.service.impl;

import com.piles.common.business.IPushBusiness;
import com.piles.control.entity.RemoteClosePushRequest;
import com.piles.control.service.IRemoteClosePushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 远程关闭充电给充电桩发送消息实现类
 */
@Service
public class RemoteClosePushServiceImpl implements IRemoteClosePushService {
    @Autowired
    IPushBusiness pushBusiness;
    @Override
    public boolean doPush(RemoteClosePushRequest remoteClosePushRequest) {
        byte[] pushMsg=RemoteClosePushRequest.packBytes(remoteClosePushRequest);
        return pushBusiness.push(pushMsg,remoteClosePushRequest.getPileNo());
    }
}
