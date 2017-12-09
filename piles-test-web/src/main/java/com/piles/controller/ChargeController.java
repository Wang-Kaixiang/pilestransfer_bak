package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.util.ChannelMap;
import com.piles.control.entity.RemoteStartPushRequest;
import com.piles.control.entity.RemoteStartRequest;
import com.piles.control.service.IRemoteStartPushService;
import com.piles.entity.vo.ChargeRemoteStartRequest;
import com.piles.util.Util;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

import static com.piles.common.entity.type.EPushResponseCode.READ_OK;

@Slf4j
@Controller
@RequestMapping("/charge")
public class ChargeController {

    @Resource
    private IRemoteStartPushService remoteStartPushService;

    /**
     * 启动充电
     *
     * @param remoteStartRequest
     * @return
     */
    @RequestMapping(value = "/charge", method = RequestMethod.POST)
    @ResponseBody
    public String charge(ChargeRemoteStartRequest remoteStartRequest) {
        Map<String,Object> map= new HashedMap();
        //check 参数
        if (StringUtils.isEmpty(remoteStartRequest.getPileNo())){
            map.put("status","-1");
            map.put("msg","充电桩编号为空");
            return JSON.toJSONString(map);
        }
        if (Util.orderNo2Seril.containsKey(remoteStartRequest.getOrderNo())&&null!=Util.getChargePushOrderOk(String.valueOf(remoteStartRequest.getOrderNo()))){
            map.put("status","200");
            map.put("msg","启动充电发送命令成功,详细结果见结果");
            map.put("data",Util.getChargePushOrderOk(String.valueOf(remoteStartRequest.getOrderNo())));
            return JSON.toJSONString(map);
        }
        if (StringUtils.isEmpty(remoteStartRequest.getGunNo())||1!=remoteStartRequest.getGunNo()){
            map.put("status","-1");
            map.put("msg","充电桩枪号不可用");
            return JSON.toJSONString(map);
        }
        if (StringUtils.isEmpty(remoteStartRequest.getOrderNo())){
            map.put("status","-1");
            map.put("msg","订单号不可用");
            return JSON.toJSONString(map);
        }
        if (StringUtils.isEmpty(remoteStartRequest.getChargeModel())
                ||1!=remoteStartRequest.getChargeModel()
                ||2!=remoteStartRequest.getChargeModel()
                ||3!=remoteStartRequest.getChargeModel()
                ||4!=remoteStartRequest.getChargeModel()
                ){
            map.put("status","-1");
            map.put("msg","充电模式不可用");
            return JSON.toJSONString(map);
        }
        //获取连接channel 获取不到无法推送
        Channel channel= ChannelMap.getChannel(remoteStartRequest.getPileNo());
        if (null==channel){
            map.put("status","400");
            map.put("msg","充电桩链接不可用");
            return JSON.toJSONString(map);
        }



        RemoteStartPushRequest remoteStartPushRequest = new RemoteStartPushRequest();
        remoteStartPushRequest.setGunNo(remoteStartRequest.getGunNo());
        remoteStartPushRequest.setOrderNo(remoteStartRequest.getOrderNo());
        remoteStartPushRequest.setPileNo(remoteStartRequest.getPileNo());
//        remoteStartPushRequest.setPileNo("0000000080000004");
        remoteStartPushRequest.setSerial(Util.getNum());
        remoteStartPushRequest.setChargeData(remoteStartRequest.getChargeData());
        remoteStartPushRequest.setChargeModel(remoteStartRequest.getChargeModel());
        remoteStartPushRequest.setChargeStopCode(StringUtils.isEmpty(remoteStartRequest.getChargeStopCode()) ? "6464" : remoteStartRequest.getChargeStopCode());
        BasePushCallBackResponse<RemoteStartRequest> remoteStartRequestBasePushCallBackResponse= remoteStartPushService.doPush(remoteStartPushRequest);
        int i=0;
        while (remoteStartRequestBasePushCallBackResponse.getCode()!=READ_OK){
            //重试2次 总共调用三次

            remoteStartRequestBasePushCallBackResponse= remoteStartPushService.doPush(remoteStartPushRequest);
            i++;
            if (i>=2){
                break;
            }
        }

        log.info("远程启动充电请求返回报文:{}", JSON.toJSONString(remoteStartRequestBasePushCallBackResponse));

        map.put("status",remoteStartRequestBasePushCallBackResponse.getCode());

        switch (remoteStartRequestBasePushCallBackResponse.getCode().getCode()){
            case 200:
                map.put("msg","启动充电发送命令成功,详细结果见结果");
                map.put("data",remoteStartRequestBasePushCallBackResponse.getObj());
                Util.chargePushOrderOk.put(String.valueOf(remoteStartPushRequest.getSerial()),remoteStartRequestBasePushCallBackResponse.getObj());
                break;
            case 300:
            case 100:
                map.put("status",300);
                map.put("msg","请求超时");
                break;
            case 400:
                map.put("msg","充电桩链接不可用");
                break;
            default:
                break;

        }

        return JSON.toJSONString(map);

    }

    /**
     * 启动充电
     *
     * @param remoteStartRequest
     * @return
     */
    @RequestMapping(value = "/appendCharge", method = RequestMethod.POST)
    @ResponseBody
    public String appendharge(ChargeRemoteStartRequest remoteStartRequest) {
        Map<String,Object> map= new HashedMap();
        //check 参数
        if (StringUtils.isEmpty(remoteStartRequest.getPileNo())){
            map.put("status","-1");
            map.put("msg","充电桩编号为空");
            return JSON.toJSONString(map);
        }
        if (Util.orderNo2Seril.containsKey(remoteStartRequest.getOrderNo())&&null!=Util.getChargePushOrderOk(String.valueOf(remoteStartRequest.getOrderNo()))){
            map.put("status","200");
            map.put("msg","追加充电发送命令成功,详细结果见结果");
            map.put("data",Util.getChargePushOrderOk(String.valueOf(remoteStartRequest.getOrderNo())));
            return JSON.toJSONString(map);
        }
        if (StringUtils.isEmpty(remoteStartRequest.getGunNo())||1!=remoteStartRequest.getGunNo()){
            map.put("status","-1");
            map.put("msg","充电桩枪号不可用");
            return JSON.toJSONString(map);
        }
        if (StringUtils.isEmpty(remoteStartRequest.getOrderNo())){
            map.put("status","-1");
            map.put("msg","订单号不可用");
            return JSON.toJSONString(map);
        }

        //获取连接channel 获取不到无法推送
        Channel channel= ChannelMap.getChannel(remoteStartRequest.getPileNo());
        if (null==channel){
            map.put("status","400");
            map.put("msg","充电桩链接不可用");
            return JSON.toJSONString(map);
        }



        RemoteStartPushRequest remoteStartPushRequest = new RemoteStartPushRequest();
        remoteStartPushRequest.setGunNo(remoteStartRequest.getGunNo());
        remoteStartPushRequest.setOrderNo(remoteStartRequest.getOrderNo());
        remoteStartPushRequest.setPileNo(remoteStartRequest.getPileNo());
        remoteStartPushRequest.setSerial(Util.getNum());
        remoteStartPushRequest.setChargeData(remoteStartRequest.getChargeData());
        remoteStartPushRequest.setChargeModel(5);
        remoteStartPushRequest.setChargeStopCode(StringUtils.isEmpty(remoteStartRequest.getChargeStopCode()) ? "6464" : remoteStartRequest.getChargeStopCode());
        BasePushCallBackResponse<RemoteStartRequest> remoteStartRequestBasePushCallBackResponse= remoteStartPushService.doPush(remoteStartPushRequest);
        int i=0;
        while (remoteStartRequestBasePushCallBackResponse.getCode()!=READ_OK){
            //重试2次 总共调用三次

            remoteStartRequestBasePushCallBackResponse= remoteStartPushService.doPush(remoteStartPushRequest);
            i++;
            if (i>=2){
                break;
            }
        }

        log.info("远程启动充电追加充电请求返回报文:{}", JSON.toJSONString(remoteStartRequestBasePushCallBackResponse));

        map.put("status",remoteStartRequestBasePushCallBackResponse.getCode());

        switch (remoteStartRequestBasePushCallBackResponse.getCode().getCode()){
            case 200:
                map.put("msg","追加充电发送命令成功,详细结果见结果");
                map.put("data",remoteStartRequestBasePushCallBackResponse.getObj());
                Util.chargePushOrderOk.put(String.valueOf(remoteStartPushRequest.getSerial()),remoteStartRequestBasePushCallBackResponse.getObj());

                break;
            case 300:
            case 100:
                map.put("status",300);
                map.put("msg","请求超时");
                break;
            case 400:
                map.put("msg","充电桩链接不可用");
                break;
            default:
                break;

        }

        return JSON.toJSONString(map);

    }

}
