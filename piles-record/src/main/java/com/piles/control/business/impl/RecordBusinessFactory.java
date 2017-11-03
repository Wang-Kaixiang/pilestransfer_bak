package com.piles.control.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.business.IBusinessFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class RecordBusinessFactory implements IBusinessFactory {

    @Resource
    private IBusiness verifyTimeBusiness;
    @Resource
    private IBusiness qrSetBusiness;
    @Resource
    private IBusiness rebootBusiness;
    @Resource
    private IBusiness remoteUpdateBusiness;
    @Resource
    private IBusiness billRuleSetBusiness;
    @Resource

    @Override
    public IBusiness getByOrder(byte order){

        switch (order){
            case 0x0E:
                //校时
                return verifyTimeBusiness;
            case 0x1C:
                //二维码设置
                return qrSetBusiness;
            case 0x1D:
                //重启
                return rebootBusiness;
            case 0x1E:
                //远程升级
                return remoteUpdateBusiness;
            case 0x0B:
                //计费规则设置
                return billRuleSetBusiness;
            default:
                return null;
        }
    }

}
