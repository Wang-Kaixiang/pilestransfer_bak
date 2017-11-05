package com.piles.control.business.impl;

import com.google.common.primitives.Bytes;
import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.util.BytesUtil;
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
    protected byte[] processBody(byte[] bodyBytes,Channel incoming) {
        //依照报文体规则解析报文
        RemoteStartRequest remoteStartRequest = RemoteStartRequest.packEntity(bodyBytes);
        //调用底层接口
        boolean flag = remoteStartService.remoteStart(remoteStartRequest);
        log.info("远程启动充电调用结果"+flag+" "+remoteStartRequest.toString());
        //组装返回报文体
        return null;
    }

    @Override
    public ECommandCode getReponseCode() {
        //TODO 设置返回码
        return null;
    }
}
