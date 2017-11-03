package com.piles.control.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.business.IBusinessFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ControlBusinessFactory implements IBusinessFactory {

    @Resource
    private IBusiness loginBusiness;
    @Resource
    private IBusiness remoteStartBusiness;
    @Resource
    private IBusiness remoteCloseBusiness;

    @Override
    public IBusiness getByOrder(byte order){

        switch (order){
            case 0x01:
                //登陆
                return loginBusiness;
            case 0x06:
                //远程启动充电
                return remoteStartBusiness;
            case 0x07:
                //远程结束充电
                return remoteCloseBusiness;
            default:
                return null;
        }
    }

}
