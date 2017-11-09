package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.control.entity.RemoteClosePushRequest;
import com.piles.control.entity.RemoteStartPushRequest;
import com.piles.control.service.ILoginService;
import com.piles.control.service.IRemoteClosePushService;
import com.piles.control.service.IRemoteStartPushService;
import com.piles.setting.entity.*;
import com.piles.setting.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/piles")
public class PilesController {

    @Resource
    private IRemoteStartPushService remoteStartPushService;
    @Resource
    private IRemoteClosePushService remoteClosePushService;
    @Resource
    private IRemoteUpdatePushService remoteUpdatePushService;
    @Resource
    private IVerifyTimePushService verifyTimePushService;
    @Resource
    private IRebootPushService rebootPushService;
    @Resource
    private IBillRuleSetPushService billRuleSetPushService;
    @RequestMapping("/test")
    public void test(HttpServletRequest request){
        String type = request.getParameter("type");
        switch (type){
            case "1":
                RemoteStartPushRequest remoteStartPushRequest= new RemoteStartPushRequest();
                remoteStartPushRequest.setGunNo( 1 );
                remoteStartPushRequest.setOrderNo( 123456L );
                remoteStartPushRequest.setPileNo( "1234567812345678" );
                remoteStartPushRequest.setSerial( "0000" );
                remoteStartPushRequest.setChargeData( new BigDecimal( 2 ) );
                remoteStartPushRequest.setChargeModel( 4 );
                remoteStartPushRequest.setChargeStopCode( "6464" );
                log.info("远程启动充电请求返回报文:{}", JSON.toJSONString(remoteStartPushService.doPush(remoteStartPushRequest)));

            case "2":
                RemoteClosePushRequest remoteClosePushRequest = new RemoteClosePushRequest();
                remoteClosePushRequest.setGunNo( 1 );
                remoteClosePushRequest.setOrderNo( 123456L );
                remoteClosePushRequest.setPileNo( "1000025484561835" );
                remoteClosePushRequest.setSerial( "0000" );
                log.info("远程结束充电请求返回报文:{}", JSON.toJSONString(remoteClosePushService.doPush(remoteClosePushRequest)));

            case "3":
                RemoteUpdatePushRequest remoteUpdatePushRequest = new RemoteUpdatePushRequest();
                remoteUpdatePushRequest.setPileNo( "8765432187654321" );
                remoteUpdatePushRequest.setSerial( "0000" );
                remoteUpdatePushRequest.setMd5( "asdfghjkasdfghjkasdfghjkasdfghjk" );
                remoteUpdatePushRequest.setProtocolVersion( "V1.0" );
                remoteUpdatePushRequest.setSoftVersion( "V1.23" );
                String url = "http://essrus";
                remoteUpdatePushRequest.setUrl( url );
                remoteUpdatePushRequest.setUrlLen( url.length() );
                log.info("远程升级请求返回报文:{}", JSON.toJSONString(remoteUpdatePushService.doPush(remoteUpdatePushRequest)));

            case "4":

                VerifyTimePushRequest verifyTimePushRequest = new VerifyTimePushRequest();
                verifyTimePushRequest.setPileNo( "1111111111111111" );
                verifyTimePushRequest.setSerial( "0000" );
                verifyTimePushRequest.setServerTime( "20171107183025" );
                log.info("校时请求返回报文:{}", JSON.toJSONString(verifyTimePushService.doPush(verifyTimePushRequest)));

            case "5":
                RebootPushRequest rebootPushRequest = new RebootPushRequest();
                rebootPushRequest.setPileNo( "1000025484561835" );
                rebootPushRequest.setSerial( "0000" );

                log.info("重启请求返回报文:{}", JSON.toJSONString(rebootPushService.doPush(rebootPushRequest)));

            case "6":
                BillRuleSetPushRequest billRuleSetPushRequest = new BillRuleSetPushRequest();
                billRuleSetPushRequest.setPileNo( "1111121131111111" );
                billRuleSetPushRequest.setSerial( "0000" );
                billRuleSetPushRequest.setBillingRuleId(4);
                billRuleSetPushRequest.setBillingRuleVersion(0);
                billRuleSetPushRequest.setEffectiveTime("171109160712");
                billRuleSetPushRequest.setSubscriptionPriceUnit(new BigDecimal(1));
                billRuleSetPushRequest.setServicePriceUnit(new BigDecimal(2));
                billRuleSetPushRequest.setParkingPriceUnit(new BigDecimal(3));
                billRuleSetPushRequest.setPointElectricPrice(new BigDecimal(4));
                billRuleSetPushRequest.setPeakElectricPrice(new BigDecimal(5));
                billRuleSetPushRequest.setOrdinaryElectricPrice(new BigDecimal(6));
                billRuleSetPushRequest.setDipElectricPrice(new BigDecimal(7));
                billRuleSetPushRequest.setPeriodCount(2);
                List<BillRulePeriod> billRulePeriodList = Lists.newArrayList();
                billRuleSetPushRequest.setBillRulePeriodList(billRulePeriodList);
                BillRulePeriod billRulePeriod1 = new BillRulePeriod();
                BillRulePeriod billRulePeriod2 = new BillRulePeriod();
                billRulePeriodList.add(billRulePeriod1);
                billRulePeriodList.add(billRulePeriod2);
                billRulePeriod1.setStartTime("2230");
                billRulePeriod1.setEndTime("2235");
                billRulePeriod1.setPeriodType(1);
                billRulePeriod2.setStartTime("2330");
                billRulePeriod2.setEndTime("2335");
                billRulePeriod2.setPeriodType(2);

                log.info("计费规则设置请求返回报文:{}", JSON.toJSONString( billRuleSetPushService.doPush(billRuleSetPushRequest)));
        }

        System.out.println("gggg");
    }
}
