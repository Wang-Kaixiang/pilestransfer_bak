package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.piles.common.util.ChannelMapByEntity;
import com.piles.entity.enums.ResponseCode;
import com.piles.entity.vo.CheckConnectionRequest;
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
     * @param remoteStartRequest
     * @return
     */
    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> charge(CheckConnectionRequest remoteStartRequest) {
        log.info( "查询链接是否可用信息:" + JSON.toJSONString( remoteStartRequest ) );
        Map<String, Object> map = new HashedMap();
        map = checkParams( remoteStartRequest );
        if (MapUtils.isNotEmpty( map )) {
            return map;
        }
        Channel channel = ChannelMapByEntity.getChannel( remoteStartRequest.getTradeTypeCode(), remoteStartRequest.getPileNo() );
        if (null == channel) {
            map.put( "status", ResponseCode.CONNECNTION_ERROR.getCode() );
            map.put( "msg", ResponseCode.CONNECNTION_ERROR.getMsg() );
        } else {
            Map<String, String> data = new HashedMap();
            map.put( "status", ResponseCode.OK.getCode() );
            map.put( "msg", ResponseCode.OK.getMsg() );
            map.put( "data", data );
            data.put( "connection", channel.remoteAddress().toString() );
        }
        log.info( "return查询链接是否可用信息:" + JSON.toJSONString( map ) );
        return map;

    }


    private Map<String, Object> checkParams(CheckConnectionRequest checkConnectionRequest) {
        Map<String, Object> map = new HashedMap();
        //check 参数

        if (StringUtils.isEmpty( checkConnectionRequest.getTradeTypeCode() )) {
            map.put( "status", "-1" );
            map.put( "msg", "充电桩厂商类型为空" );
            log.info( "return请求充电请求fan:" + JSON.toJSONString( map ) );
            return map;
        }
        if (StringUtils.isEmpty( checkConnectionRequest.getPileNo() )) {
            map.put( "status", "-1" );
            map.put( "msg", "充电桩编号为空" );
            log.info( "return请求充电请求fan:" + JSON.toJSONString( map ) );
            return map;
        }

        return map;

    }

}
