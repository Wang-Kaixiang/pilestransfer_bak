package com.piles.control.business.impl;

import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.control.entity.RemoteStartRequest;
import com.piles.control.service.IRemoteStartService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 远程启动充电  运营管理系统 -> 充电桩
 */
@Slf4j
@Service("remoteStartBusiness")
public class RemoteStartBusinessImpl extends BaseBusiness{

    //设置返回报文头命令

    @Resource
    private IRemoteStartService remoteStartService;

    @Override
    protected byte[] processBody(byte[] bodyBytes,Channel incoming,int order) {
        //依照报文体规则解析报文
        RemoteStartRequest remoteStartRequest = RemoteStartRequest.packEntity(bodyBytes);
        //调用底层接口
        ChannelResponseCallBackMap.callBack( incoming,String.valueOf( order ),remoteStartRequest );
        return null;
    }

    @Override
    public ECommandCode getReponseCode() {
        //TODO 设置返回码
        return null;
    }
}
