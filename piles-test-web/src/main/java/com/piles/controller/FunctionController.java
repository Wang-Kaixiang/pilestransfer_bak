package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.entity.BasePushRequest;
import com.piles.common.entity.type.TradeType;
import com.piles.common.util.ChannelMapByEntity;
import com.piles.common.util.GunElecAmountMapUtil;
import com.piles.common.util.GunStatusMapUtil;
import com.piles.common.util.GunWorkStatusMapUtil;
import com.piles.control.entity.RemoteStartPushRequest;
import com.piles.control.entity.RemoteStartRequest;
import com.piles.control.service.IRemoteStartPushService;
import com.piles.entity.enums.ResponseCode;
import com.piles.entity.vo.CheckConnectionRequest;
import com.piles.entity.vo.PileChargeStatusRequest;
import com.piles.entity.vo.PileStatusRequest;
import com.piles.record.entity.XunDaoChargeMonitorRequest;
import com.piles.record.service.IChargeMonitorPushService;
import com.piles.util.ServiceFactoryUtil;
import com.piles.util.Util;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

import static com.piles.common.entity.type.EPushResponseCode.CONNECT_ERROR;
import static com.piles.common.entity.type.EPushResponseCode.READ_OK;

@Slf4j
@Controller
@RequestMapping("/tool")
public class FunctionController {
    @Resource
    ServiceFactoryUtil serviceFactoryUtil;


    /**
     * 查询链接是否可用
     *
     * @param checkConnectionRequest
     * @return
     */
    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> charge(CheckConnectionRequest checkConnectionRequest) {
        log.info("查询链接是否可用信息:" + JSON.toJSONString(checkConnectionRequest));
        Map<String, Object> map = new HashedMap();
        map = checkParams(checkConnectionRequest.getTradeTypeCode(),checkConnectionRequest.getPileNo());
        if (MapUtils.isNotEmpty(map)) {
            return map;
        }
        Channel channel = ChannelMapByEntity.getChannel(checkConnectionRequest.getTradeTypeCode(), checkConnectionRequest.getPileNo());
        if (null == channel) {
            map.put("status", ResponseCode.CONNECNTION_ERROR.getCode());
            map.put("msg", ResponseCode.CONNECNTION_ERROR.getMsg());
        } else {
            Map<String, String> data = new HashedMap();
            map.put("status", ResponseCode.OK.getCode());
            map.put("msg", ResponseCode.OK.getMsg());
            map.put("data", data);
            data.put("connection", channel.remoteAddress().toString());
        }
        log.info("return查询链接是否可用信息:" + JSON.toJSONString(map));
        return map;

    }

    /**
     * 查询枪状态是否可用
     *
     * @param pileStatusRequest
     * @return
     */
    @RequestMapping(value = "/pileStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> status(PileStatusRequest pileStatusRequest) {
        log.info("查询枪状态是否可用信息:" + JSON.toJSONString(pileStatusRequest));
        Map<String, Object> map = new HashedMap();
        map = checkParams(pileStatusRequest.getTradeTypeCode(),pileStatusRequest.getPileNo());
        if (MapUtils.isNotEmpty(map)) {
            log.info("return请求充电请求fan:" + JSON.toJSONString(map));
            return map;
        }
        Integer status = GunStatusMapUtil.get(pileStatusRequest.getPileNo(), pileStatusRequest.getTradeTypeCode());
        if (null == status) {
            map.put("status", ResponseCode.NO_STATUS.getCode());
            map.put("msg", ResponseCode.NO_STATUS.getMsg());
        } else {
            Map<String, Object> data = new HashedMap();
            map.put("status", ResponseCode.OK.getCode());
            map.put("msg", ResponseCode.OK.getMsg());
            map.put("data", data);
            boolean canCharged = false;

            switch (pileStatusRequest.getTradeTypeCode()) {
                case 1:
                    if (status == 0) {
                        canCharged = true;
                    }
                    break;
                case 2:
                    BigDecimal highestAllowElectricity = GunElecAmountMapUtil.get(pileStatusRequest.getPileNo(), pileStatusRequest.getTradeTypeCode());
                    String workStatus = GunWorkStatusMapUtil.get(pileStatusRequest.getPileNo(), pileStatusRequest.getTradeTypeCode());
                    //当电流存在，并且大于0小于等于1的时候返回true
                    if (!"01".equals(workStatus) &&
                            (status == 1 || (status == 2 && (highestAllowElectricity != null &&
                            highestAllowElectricity.compareTo(BigDecimal.ZERO) >= 0 &&
                            highestAllowElectricity.compareTo(BigDecimal.ONE) <= 0)))) {
                        canCharged = true;
                    }
                    break;
            }
            data.put("canCharged", canCharged);
            data.put("gunStatus", status);
        }
        log.info("return查询枪状态是否可用信息:" + JSON.toJSONString(map));
        return map;

    }

    /**
     * 查询枪状态是否可用
     *
     * @param pileChargeStatusRequest
     * @return
     */
    @RequestMapping(value = "/pileChargeStatus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> chargeStatus(PileChargeStatusRequest pileChargeStatusRequest) {
        log.info("查询充电桩充电进度:" + JSON.toJSONString(pileChargeStatusRequest));
        Map<String, Object> map = new HashedMap();
        map = checkParams(pileChargeStatusRequest.getTradeTypeCode(),pileChargeStatusRequest.getPileNo());
        if (MapUtils.isNotEmpty(map)) {
            log.info("return充电桩充电进度fan:" + JSON.toJSONString(map));
            return map;
        }
        if (TradeType.WEI_JING.getCode()==pileChargeStatusRequest.getTradeTypeCode()){
            map.put("status", "-1");
            map.put("msg", "充电桩不支持查询充电进度");
            log.info("return请充电桩充电进度fan:"+JSON.toJSONString(map));
            return map;
        }
        Channel channel = ChannelMapByEntity.getChannel(pileChargeStatusRequest.getTradeTypeCode(), pileChargeStatusRequest.getPileNo());
        if (null == channel) {
            map.put("status", ResponseCode.CONNECNTION_ERROR.getCode());
            map.put("msg", ResponseCode.CONNECNTION_ERROR.getMsg());
        } else {
            IChargeMonitorPushService iChargeMonitorPushService=serviceFactoryUtil.getService(pileChargeStatusRequest.getTradeTypeCode(),IChargeMonitorPushService.class);
            BasePushRequest basePushRequest=new BasePushRequest();
            basePushRequest.setPileNo(pileChargeStatusRequest.getPileNo()  );
            basePushRequest.setTradeTypeCode( pileChargeStatusRequest.getTradeTypeCode() );
            basePushRequest.setSerial( pileChargeStatusRequest.getSerial() );
            BasePushCallBackResponse<XunDaoChargeMonitorRequest> xunDaoChargeMonitorRequestBasePushCallBackResponse = iChargeMonitorPushService.doPush(basePushRequest);

            if (xunDaoChargeMonitorRequestBasePushCallBackResponse.getCode() != READ_OK) {
                //重试1
                xunDaoChargeMonitorRequestBasePushCallBackResponse = iChargeMonitorPushService.doPush(basePushRequest);
            }
            switch (xunDaoChargeMonitorRequestBasePushCallBackResponse.getCode()) {
                case READ_OK:
                    map.put("status", READ_OK.getCode());
                    map.put("msg", "查询电量进度成功,详细结果见结果");
                    map.put("data", xunDaoChargeMonitorRequestBasePushCallBackResponse.getObj());
                    break;
                case TIME_OUT:
                case WRITE_OK:
                    map.put("status", 300);
                    map.put("msg", "请求超时");
                    break;
                case CONNECT_ERROR:
                    map.put("status", CONNECT_ERROR.getCode());
                    map.put("msg", "充电桩链接不可用");
                    break;
                default:
                    break;

            }
        }

        log.info("return查询充电桩充电进度:" + JSON.toJSONString(map));
        return map;

    }



    private Map<String, Object> checkParams(Integer tradeTypeCode,String pileNo) {
        Map<String, Object> map = new HashedMap();
        //check 参数

        if (StringUtils.isEmpty(tradeTypeCode)) {
            map.put("status", "-1");
            map.put("msg", "充电桩厂商类型为空");
            return map;
        }
        if (StringUtils.isEmpty(pileNo)) {
            map.put("status", "-1");
            map.put("msg", "充电桩编号为空");

            return map;
        }
        //获取连接channel 获取不到无法推送
        Channel channel = ChannelMapByEntity.getChannel(tradeTypeCode, pileNo);
        if (null == channel) {
            map.put("status", "400");
            map.put("msg", "充电桩不在线");
            return map;
        }
        return map;

    }


}
