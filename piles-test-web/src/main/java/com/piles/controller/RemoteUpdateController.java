package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.entity.vo.UpdateRemoteRequest;
import com.piles.setting.entity.RemoteUpdatePushRequest;
import com.piles.setting.entity.RemoteUpdateRequest;
import com.piles.setting.service.IRemoteUpdatePushService;
import com.piles.util.ServiceFactoryUtil;
import com.piles.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.piles.common.entity.type.EPushResponseCode.*;

/**
 * 远程升级接口
 */
@Slf4j
@Controller
@RequestMapping("/remoteUpdate")
public class RemoteUpdateController {
    @Resource
    ServiceFactoryUtil serviceFactoryUtil;


    /**
     * 蔚景远程升级
     *
     * @param updateRemoteRequest
     * @return
     */
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> charge(UpdateRemoteRequest updateRemoteRequest) {
        log.info("请求远程升级请求信息:"+JSON.toJSONString(updateRemoteRequest));
        Map<String, Object> map = new HashedMap();
        map=checkParams(updateRemoteRequest);
        if (null!=map){
            return map;
        }
        map = new HashedMap();

        IRemoteUpdatePushService iRemoteUpdatePushService=serviceFactoryUtil.getService(updateRemoteRequest.getTradeTypeCode(),IRemoteUpdatePushService.class);
        String[] pileList = StringUtils.split(updateRemoteRequest.getPileNos(), ",");
        List<RemoteUpdatePushRequest> RemoteUpdatePushRequestList = Arrays.stream(pileList).map(s -> {
            RemoteUpdatePushRequest remoteUpdatePushRequest = new RemoteUpdatePushRequest();
            remoteUpdatePushRequest.setTradeTypeCode(updateRemoteRequest.getTradeTypeCode());
            remoteUpdatePushRequest.setPileNo(s);
            remoteUpdatePushRequest.setSerial(updateRemoteRequest.getSerial());
            remoteUpdatePushRequest.setSoftVersion(updateRemoteRequest.getSoftVersion());
            remoteUpdatePushRequest.setProtocolVersion(updateRemoteRequest.getProtocolVersion());
            return remoteUpdatePushRequest;
        }).collect(Collectors.toList());
        BasePushCallBackResponse<RemoteUpdateRequest> remoteUpdateRequestBasePushCallBackResponse = iRemoteUpdatePushService.doBatchPush(RemoteUpdatePushRequestList);

//        if (remoteUpdateRequestBasePushCallBackResponse.getCode() != READ_OK) {
//            //重试1
//            remoteUpdateRequestBasePushCallBackResponse = iRemoteUpdatePushService.doPush(remoteUpdatePushRequest);
//        }
//        log.info("远程升级请求返回报文:{}", JSON.toJSONString(remoteUpdateRequestBasePushCallBackResponse));


        switch (remoteUpdateRequestBasePushCallBackResponse.getCode()) {
            case READ_OK:
                map.put("status", READ_OK.getCode());
                map.put("msg", "远程升级发送命令成功,详细结果见结果");
                map.put("data", remoteUpdateRequestBasePushCallBackResponse.getObj());
                //TODO 这是什么意思
//                Util.chargePushOrderOk.put(String.valueOf(remoteUpdatePushRequest.getSerial()), remoteUpdateRequestBasePushCallBackResponse.getObj());
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

        log.info("return请求充电请求fan:"+JSON.toJSONString(map));
        return map;

    }

    private Map<String, Object> checkParams(UpdateRemoteRequest remoteUpdateRequest){
        Map<String, Object> map = new HashedMap();
        //check 参数
        int serial=0;



        if (StringUtils.isEmpty(remoteUpdateRequest.getTradeTypeCode())) {
            map.put("status", "-1");
            map.put("msg", "充电桩厂商类型为空");
            log.info("return请求远程升级请求fan:"+JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getPileNos())) {
            map.put("status", "-1");
            map.put("msg", "充电桩编号为空");
            log.info("return请求远程升级请求fan:"+JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getSerial())) {
            map.put("status", "-1");
            map.put("msg", "流水号为空");
            log.info("return请求远程升级请求fan:"+JSON.toJSONString(map));
            return map;
        }
        try {
            serial=Integer.parseInt( remoteUpdateRequest.getSerial() );
            if (serial>65535){
                map.put("status", "-1");
                map.put("msg", "流水号不能大于65535");
                log.info("return请求远程升级请求fan:"+JSON.toJSONString(map));
            }
        }catch (Exception e){
            map.put("status", "-1");
            map.put("msg", "流水号需要是数字");
            log.info("return请求远程升级请求fan:"+JSON.toJSONString(map));
            return map;
        }

        if (StringUtils.isEmpty(remoteUpdateRequest.getSoftVersion())) {
            map.put("status", "-1");
            map.put("msg", "软件版本号为空");
            log.info("return请求远程升级请求fan:"+JSON.toJSONString(map));
            return map;
        }

        //获取连接channel 获取不到无法推送
//        Channel channel = ChannelMapByEntity.getChannel(remoteUpdateRequest.getTradeTypeCode(),remoteUpdateRequest.getPileNo());
//        if (null == channel) {
//            map.put("status", "400");
//            map.put("msg", "充电桩链接不可用");
//            log.info("return请求充电请求fan:"+JSON.toJSONString(map));
//            return map;
//        }
        return null;

    }

}
