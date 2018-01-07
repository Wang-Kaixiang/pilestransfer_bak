package com.piles.util;

import com.google.common.collect.Maps;
import com.piles.control.service.IRemoteStartPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lgc on 18/1/7.
 */
@Service
public class ServiceFactoryUtil {

    @Autowired
    SpringBeanFactoryUtils springBeanFactoryUtils;

    static Map<String,Object> services= Maps.newConcurrentMap();

    /**
     * 获取开始充电serve
     * @param tradeTypeCode
     * @return
     */
    public IRemoteStartPushService getStartPushService(int tradeTypeCode){
        if (!services.containsKey("IRemoteStartPushService"+tradeTypeCode)){
            String[] names=springBeanFactoryUtils.getBeanNames(IRemoteStartPushService.class);
            for (String name:names){
                String code=name.split("_")[1];
                services.put("IRemoteStartPushService"+code,springBeanFactoryUtils.getBean(name));
            }
        }
        return (IRemoteStartPushService) services.get("IRemoteStartPushService"+tradeTypeCode);
    }
}
