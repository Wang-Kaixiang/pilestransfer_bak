package com.piles.control.business.impl;

import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.control.entity.RemoteCloseRequest;
import com.piles.control.service.IRemoteCloseService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 远程结束充电
 */
@Slf4j
@Service("remoteCloseBusiness")
public class RemoteCloseBusinessImpl extends BaseBusiness{

    //设置返回报文头命令

    @Resource
    private IRemoteCloseService remoteCloseService;



    @Override
    protected byte[] processBody(byte[] bodyBytes,Channel incoming,int order) {
        //依照报文体规则解析报文
        RemoteCloseRequest remoteCloseRequest = RemoteCloseRequest.packEntity(bodyBytes);
        ChannelResponseCallBackMap.callBack( incoming,String.valueOf( order ),remoteCloseRequest );
        return null;
    }

    @Override
    public ECommandCode getReponseCode() {
        //TODO 设置返回码
        return null;
    }
}
