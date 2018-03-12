package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.piles.common.entity.type.TradeType;
import com.piles.common.util.ChannelMapByEntity;
import com.piles.common.util.GunElecAmountMapUtil;
import com.piles.common.util.GunStatusMapUtil;
import com.piles.entity.enums.ResponseCode;
import com.piles.entity.vo.CheckConnectionRequest;
import com.piles.entity.vo.PileStatusRequest;
import com.piles.util.ServiceFactoryUtil;
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
        map = checkParams(checkConnectionRequest);
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
        map = checkParams(pileStatusRequest);
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
                    //当电流存在，并且大于0小于等于1的时候返回true
                    if (status == 1 || (status == 2 && (highestAllowElectricity != null &&
                            highestAllowElectricity.compareTo(BigDecimal.ZERO) >= 0 &&
                            highestAllowElectricity.compareTo(BigDecimal.ONE) <= 1))) {
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


    private Map<String, Object> checkParams(CheckConnectionRequest checkConnectionRequest) {
        Map<String, Object> map = new HashedMap();
        //check 参数

        if (StringUtils.isEmpty(checkConnectionRequest.getTradeTypeCode())) {
            map.put("status", "-1");
            map.put("msg", "充电桩厂商类型为空");
            log.info("return请求充电请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(checkConnectionRequest.getPileNo())) {
            map.put("status", "-1");
            map.put("msg", "充电桩编号为空");
            log.info("return请求充电请求fan:" + JSON.toJSONString(map));
            return map;
        }

        return map;

    }

    private Map<String, Object> checkParams(PileStatusRequest pileStatusRequest) {
        Map<String, Object> map = new HashedMap();
        //check 参数

        if (StringUtils.isEmpty(pileStatusRequest.getTradeTypeCode())) {
            map.put("status", "-1");
            map.put("msg", "充电桩厂商类型为空");
            return map;
        }
        if (StringUtils.isEmpty(pileStatusRequest.getPileNo())) {
            map.put("status", "-1");
            map.put("msg", "充电桩编号为空");

            return map;
        }
        //获取连接channel 获取不到无法推送
        Channel channel = ChannelMapByEntity.getChannel(pileStatusRequest.getTradeTypeCode(), pileStatusRequest.getPileNo());
        if (null == channel) {
            map.put("status", "400");
            map.put("msg", "充电桩不在线");
            return map;
        }


        return map;

    }


}
