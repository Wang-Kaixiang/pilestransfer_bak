package com.piles.common.business.impl;

import com.piles.common.business.IBusiness;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ControlBusinessFactory {

    @Resource
    private IBusiness loginService;
    @Resource
    private IBusiness remoteStartService;
    @Resource
    private IBusiness remoteCloseService;

    public IBusiness getByOrder(byte order){

        switch (order){
            case 0x01:
                //登陆
                return loginService;
            case 0x06:
                //远程启动充电
                return remoteStartService;
            case 0x07:
                //远程结束充电
                return remoteCloseService;
            default:
                return null;
        }
    }

}
