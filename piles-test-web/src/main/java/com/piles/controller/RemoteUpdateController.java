package com.piles.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.piles.entity.vo.UpdateRemoteRequest;
import com.piles.entity.vo.XunDaoUpdateRemoteRequest;
import com.piles.setting.entity.RemoteUpdatePushRequest;
import com.piles.setting.entity.XunDaoFtpUpgradeIssuePushRequest;
import com.piles.setting.service.IRemoteUpdatePushService;
import com.piles.setting.service.IXunDaoFtpUpgradeIssueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
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

/**
 * 远程升级接口
 */
@Slf4j
@Controller
@RequestMapping("/remoteUpdate")
public class RemoteUpdateController {
    @Resource
    IRemoteUpdatePushService remoteUpdatePushService;
    @Resource
    IXunDaoFtpUpgradeIssueService xunDaoFtpUpgradeIssueService;


    /**
     * 蔚景远程升级
     *
     * @param updateRemoteRequest
     * @return
     */
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> doUpdate(UpdateRemoteRequest updateRemoteRequest) {
        log.info("请求蔚景远程升级请求信息:" + JSON.toJSONString(updateRemoteRequest));
        Map<String, Object> map = checkParams(updateRemoteRequest);
        if (null != map) {
            return Lists.newArrayList(map);
        }

        String[] pileList = StringUtils.split(updateRemoteRequest.getPileNos(), ",");
        List<RemoteUpdatePushRequest> remoteUpdateList = Arrays.stream(pileList).map(s -> {
            RemoteUpdatePushRequest remoteUpdatePushRequest = new RemoteUpdatePushRequest();
            remoteUpdatePushRequest.setTradeTypeCode(updateRemoteRequest.getTradeTypeCode());
            remoteUpdatePushRequest.setPileNo(s);
            remoteUpdatePushRequest.setSerial(updateRemoteRequest.getSerial());
            remoteUpdatePushRequest.setSoftVersion(updateRemoteRequest.getSoftVersion());
            remoteUpdatePushRequest.setProtocolVersion(updateRemoteRequest.getProtocolVersion());
            return remoteUpdatePushRequest;
        }).collect(Collectors.toList());
        List<Map> maps = remoteUpdatePushService.doBatchPush(remoteUpdateList);


        log.info("return请求蔚景远程升级请求结果{}:", JSON.toJSONString(maps));
        return maps;

    }

    private Map<String, Object> checkParams(UpdateRemoteRequest remoteUpdateRequest) {
        Map<String, Object> map = new HashedMap();
        //check 参数
        int serial = 0;


        if (StringUtils.isEmpty(remoteUpdateRequest.getTradeTypeCode())) {
            map.put("status", "-1");
            map.put("msg", "充电桩厂商类型为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getPileNos())) {
            map.put("status", "-1");
            map.put("msg", "充电桩编号为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getSerial())) {
            map.put("status", "-1");
            map.put("msg", "流水号为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        try {
            serial = Integer.parseInt(remoteUpdateRequest.getSerial());
            if (serial > 65535) {
                map.put("status", "-1");
                map.put("msg", "流水号不能大于65535");
                log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            }
        } catch (Exception e) {
            map.put("status", "-1");
            map.put("msg", "流水号需要是数字");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }

        if (StringUtils.isEmpty(remoteUpdateRequest.getSoftVersion())) {
            map.put("status", "-1");
            map.put("msg", "软件版本号为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }

        return null;

    }
    /**
     * 循道远程升级
     *
     * @param updateRemoteRequest
     * @return
     */
    @RequestMapping(value = "/doXunDaoUpdate", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> doXunDaoUpdate(XunDaoUpdateRemoteRequest updateRemoteRequest) {
        log.info("请求循道远程升级请求信息:" + JSON.toJSONString(updateRemoteRequest));
        Map<String, Object> map = checkXunDaoParams(updateRemoteRequest);
        if (null != map) {
            return Lists.newArrayList(map);
        }

        String[] pileList = StringUtils.split(updateRemoteRequest.getPileNos(), ",");
        List<XunDaoFtpUpgradeIssuePushRequest> remoteupdatelist = Arrays.stream(pileList).map(s -> {
            XunDaoFtpUpgradeIssuePushRequest remoteUpdate = new XunDaoFtpUpgradeIssuePushRequest();
            BeanUtils.copyProperties(updateRemoteRequest,remoteUpdate);
            remoteUpdate.setPileNo(s);
            return remoteUpdate;
        }).collect(Collectors.toList());
        List<Map> maps = xunDaoFtpUpgradeIssueService.doBatchPush(remoteupdatelist);


        log.info("return请求远程升级请求结果{}:", JSON.toJSONString(maps));
        return maps;

    }

    private Map<String, Object> checkXunDaoParams(XunDaoUpdateRemoteRequest remoteUpdateRequest) {
        Map<String, Object> map = new HashedMap();
        //check 参数
        int serial = 0;


        if (StringUtils.isEmpty(remoteUpdateRequest.getTradeTypeCode())) {
            map.put("status", "-1");
            map.put("msg", "充电桩厂商类型为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getPileNos())) {
            map.put("status", "-1");
            map.put("msg", "充电桩编号为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getSerial())) {
            map.put("status", "-1");
            map.put("msg", "流水号为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        try {
            serial = Integer.parseInt(remoteUpdateRequest.getSerial());
            if (serial > 65535) {
                map.put("status", "-1");
                map.put("msg", "流水号不能大于65535");
                log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            }
        } catch (Exception e) {
            map.put("status", "-1");
            map.put("msg", "流水号需要是数字");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }

        if (StringUtils.isEmpty(remoteUpdateRequest.getSoftVersion())) {
            map.put("status", "-1");
            map.put("msg", "软件版本号为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getServerIp())) {
            map.put("status", "-1");
            map.put("msg", "服务器ip为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getServerPort())) {
            map.put("status", "-1");
            map.put("msg", "服务器端口为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }
        if (StringUtils.isEmpty(remoteUpdateRequest.getUserName())) {
            map.put("status", "-1");
            map.put("msg", "用户名为空");
            log.info("return请求远程升级请求fan:" + JSON.toJSONString(map));
            return map;
        }

        return null;

    }

}
