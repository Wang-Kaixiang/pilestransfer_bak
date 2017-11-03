package com.piles.common.business.impl;

import com.piles.common.business.IService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ServiceFactory {

    @Resource
    private IService loginService;
    @Resource
    private IService remoteStartService;
    @Resource
    private IService remoteCloseService;

    public IService getByOrder(byte order){

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
