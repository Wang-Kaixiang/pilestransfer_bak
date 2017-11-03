package com.piles.common.business.impl;

import com.piles.common.business.AbstractBusinessHander;
import com.piles.common.business.IService;
import com.piles.common.entity.SocketBaseDTO;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lgc48027 on 2017/11/3.
 */
@Component
public class BusinessHandler extends AbstractBusinessHander {

    @Autowired
    ServiceFactory serviceFactory;

    @Override
    public SocketBaseDTO process1(byte[] msg, Channel incoming) {
        IService iService = null;
        iService = serviceFactory.getByOrder( msg[1] );
        if (null != iService) {
            return iService.process( msg );
        }
        return null;
    }
}
