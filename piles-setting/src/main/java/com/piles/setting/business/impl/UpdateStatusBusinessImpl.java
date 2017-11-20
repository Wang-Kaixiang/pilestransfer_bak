package com.piles.setting.business.impl;

import com.piles.common.business.BaseBusiness;
import com.piles.common.entity.type.ECommandCode;
import com.piles.setting.entity.UpdateStatusRequest;
import com.piles.setting.service.IUpdateStatusService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 升级状态汇报 充电桩  运营管理系统
 */
@Slf4j
@Service("updateStatusBusiness")
public class UpdateStatusBusinessImpl extends BaseBusiness {


    @Resource
    private IUpdateStatusService updateStatusService;

    private static byte[] response = new byte[]{};


    @Override
    protected byte[] processBody(byte[] bodyBytes, Channel incoming, int order) {

        //依照报文体规则解析报文
        UpdateStatusRequest updatePackageRequest = UpdateStatusRequest.packEntity(bodyBytes, incoming);
        updateStatusService.updateStatus(updatePackageRequest);

        //组装返回报文体
        return response;
    }

    @Override
    public ECommandCode getReponseCode() {
        return ECommandCode.UPDATE_RESULT_REPORT_ANWSER_CODE;
    }


}
