package com.piles.control.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.control.entity.RemoteCloseRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 远程结束充电
 */
@Slf4j
@Service("xunDaoStartOrStopBusiness")
public class XunDaoStartOrStopBusinessImpl implements IBusiness {





    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        String order="";//TODO 流水号
        //依照报文体规则解析报文
        RemoteCloseRequest remoteCloseRequest = RemoteCloseRequest.packEntityXunDao(msg);
        ChannelResponseCallBackMap.callBack(incoming, String.valueOf(order), remoteCloseRequest);
        return null;
    }
}
