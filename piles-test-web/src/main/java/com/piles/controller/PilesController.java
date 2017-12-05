package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.piles.Util;
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
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public void test(HttpServletRequest request) {
        String type = request.getParameter("type");
        switch (type) {
            case "1":
                RemoteStartPushRequest remoteStartPushRequest = new RemoteStartPushRequest();
                remoteStartPushRequest.setGunNo(1);
                remoteStartPushRequest.setOrderNo(123456L);
                remoteStartPushRequest.setPileNo("0000000080000004");
                remoteStartPushRequest.setSerial(0);
                remoteStartPushRequest.setChargeData(new BigDecimal(2));
                remoteStartPushRequest.setChargeModel(4);
                remoteStartPushRequest.setChargeStopCode("6464");
                log.info("远程启动充电请求返回报文:{}", JSON.toJSONString(remoteStartPushService.doPush(remoteStartPushRequest)));
                break;
            case "2":
                RemoteClosePushRequest remoteClosePushRequest = new RemoteClosePushRequest();
                remoteClosePushRequest.setGunNo(1);
                remoteClosePushRequest.setOrderNo(123456L);
                remoteClosePushRequest.setPileNo("0000000080000004");
                remoteClosePushRequest.setSerial(0);
                log.info("远程结束充电请求返回报文:{}", JSON.toJSONString(remoteClosePushService.doPush(remoteClosePushRequest)));

                break;
            case "3":
                RemoteUpdatePushRequest remoteUpdatePushRequest = new RemoteUpdatePushRequest();
                remoteUpdatePushRequest.setPileNo("0000000080000004");
                remoteUpdatePushRequest.setSerial(0);
                remoteUpdatePushRequest.setMd5("a935977e532154c6d5105a5024c65923");
                remoteUpdatePushRequest.setProtocolVersion("V1.0");
                remoteUpdatePushRequest.setSoftVersion("V1.23");
                String url = "http://59.110.170.111/piles-test-web-1.0.0/soft/AcOneV2.12.bin";
                remoteUpdatePushRequest.setUrl(url);
                remoteUpdatePushRequest.setUrlLen(url.length());
                BasePushCallBackResponse<RemoteUpdateRequest> testRs=remoteUpdatePushService.doPush(remoteUpdatePushRequest);

                if (null!=testRs.getObj()&&testRs.getObj().getUpdateType()==2){
                    Util.map.put( "0000000080000004",testRs.getObj().getDownMaxLenLimit() );
                }

                log.info("远程升级请求返回报文:{}", JSON.toJSONString(testRs));

                break;
            case "4":

                VerifyTimePushRequest verifyTimePushRequest = new VerifyTimePushRequest();
                verifyTimePushRequest.setPileNo("0000000080000004");
                verifyTimePushRequest.setSerial(0);
                Date dt= new Date(  );
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat( "yyMMddHHmmss" );

                verifyTimePushRequest.setServerTime(simpleDateFormat.format( dt ));
                log.info("校时请求返回报文:{}", JSON.toJSONString(verifyTimePushService.doPush(verifyTimePushRequest)));

                break;
            case "5":
                RebootPushRequest rebootPushRequest = new RebootPushRequest();
                rebootPushRequest.setPileNo("0000000080000004");
                rebootPushRequest.setSerial(0);

                log.info("重启请求返回报文:{}", JSON.toJSONString(rebootPushService.doPush(rebootPushRequest)));

                break;
            case "6":
                BillRuleSetPushRequest billRuleSetPushRequest = new BillRuleSetPushRequest();
                billRuleSetPushRequest.setPileNo("0000000080000004");
                billRuleSetPushRequest.setSerial(0);
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

                log.info("计费规则设置请求返回报文:{}", JSON.toJSONString(billRuleSetPushService.doPush(billRuleSetPushRequest)));
                break;
            case "7":
                RemoteStartPushRequest remoteStartPushRequest2 = new RemoteStartPushRequest();
                remoteStartPushRequest2.setGunNo(1);
                remoteStartPushRequest2.setOrderNo(123457L);
                remoteStartPushRequest2.setPileNo("0000000080000004");
                remoteStartPushRequest2.setSerial(0);
                remoteStartPushRequest2.setChargeData(new BigDecimal(2));
                remoteStartPushRequest2.setChargeModel(5);
                remoteStartPushRequest2.setChargeStopCode("6565");
                log.info("远程启动充电请求返回报文:{}", JSON.toJSONString(remoteStartPushService.doPush(remoteStartPushRequest2)));
                break;
        }

        System.out.println("gggg");
    }
}
