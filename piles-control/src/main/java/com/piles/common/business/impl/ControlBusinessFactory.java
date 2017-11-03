package com.piles.common.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.business.IBusinessFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ControlBusinessFactory implements IBusinessFactory {

    @Resource
    private IBusiness loginService;
    @Resource
    private IBusiness remoteStartService;
    @Resource
    private IBusiness remoteCloseService;

    @Override
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
