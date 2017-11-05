package com.piles.control.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
import com.piles.control.entity.LoginRequest;
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
    protected byte[] processBody(byte[] bodyBytes,Channel incoming) {
        //依照报文体规则解析报文
        RemoteCloseRequest remoteCloseRequest = RemoteCloseRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = remoteCloseService.remoteClose(remoteCloseRequest);
        log.info("远程结束充电调用结果"+flag+" "+remoteCloseRequest.toString());
        //组装返回报文体
        return null;
    }

    @Override
    public ECommandCode getReponseCode() {
        //TODO 设置返回码
        return null;
    }
}
