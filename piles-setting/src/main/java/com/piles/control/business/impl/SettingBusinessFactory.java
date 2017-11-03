package com.piles.control.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.business.IBusinessFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class SettingBusinessFactory implements IBusinessFactory{

    @Resource
    private IBusiness uploadRecordBusiness;
    @Resource
    private IBusiness uploadChargeRateBusiness;
    @Resource
    private IBusiness uploadChargeMonitorBusiness;
    @Resource
    private IBusiness heartBeatBusiness;

    @Override
    public IBusiness getByOrder(byte order){

        switch (order){
            case 0x08:
                //上传充电记录
                return uploadRecordBusiness;
            case 0x09:
                //上传充电进度
                return uploadChargeRateBusiness;
            case 0x0A:
                //上传充电过程监测数据
                return uploadChargeMonitorBusiness;
            case 0x0C:
                //心跳
                return heartBeatBusiness;
            default:
                return null;
        }
    }

}
