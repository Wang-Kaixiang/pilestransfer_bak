package com.piles.record.business.impl;

import com.piles.common.business.IBusiness;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 登录接口逻辑
 */
@Slf4j
@Component("xunDaoLockSyncBusiness")
public class XunDaoLockSyncBusinessImpl implements IBusiness {

    private static final byte[] msgRet = new byte[]{0x68, 0x04, (byte) 0x83, 0x00, 0x00, 0x00};

    @Override
    public byte[] process(byte[] msg, Channel incoming) {
        log.info( "接收到循道心跳请求报文" );
        //依照报文体规则解析报文

        //组装返回报文体

        return msgRet;
    }
}
