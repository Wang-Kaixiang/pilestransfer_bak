package com.piles.common.business;

import com.piles.common.entity.SocketBaseDTO;
import com.piles.common.util.CRC16Util;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * Created by lgc48027 on 2017/11/3.
 */
@Component
public abstract class AbstractBusinessHander  implements IBusinessHandler{
    @Override
    public SocketBaseDTO process(byte[] msg, Channel incoming) {
        //报文校验不通过则抛弃
        if (CRC16Util.checkMsg( msg )){
            return process1( msg,incoming );
        }
        return null;
    }



    public abstract SocketBaseDTO process1(byte[] msg, Channel incoming);
}
