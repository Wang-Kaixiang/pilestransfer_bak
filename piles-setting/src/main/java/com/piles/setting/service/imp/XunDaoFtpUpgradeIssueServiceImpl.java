package com.piles.setting.service.imp;

import com.google.common.primitives.Bytes;
import com.piles.common.business.IPushBusiness;
import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.common.entity.type.ECommandCode;
import com.piles.common.entity.type.EPushResponseCode;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.CRC16Util;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.setting.entity.XunDaoFtpUpgradeIssuePushRequest;
import com.piles.setting.entity.XunDaoFtpUpgradeIssueRequest;
import com.piles.setting.service.IXunDaoFtpUpgradeIssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 循道 FTP升级程序数据下行
 */
@Slf4j
@Service("ftpUpgradeIssueService_2")
public class XunDaoFtpUpgradeIssueServiceImpl implements IXunDaoFtpUpgradeIssueService {
    @Resource(name = "xunDaoPushBusinessImpl")
    IPushBusiness pushBusiness;

    //类型标识
    private int typeCode = 133;

    //记录类型
    private int recordType = 35;


    /**
     * 默认1分钟超时
     */
    @Value("${timeout:60000}")
    private long timeout;

    @Override
    public BasePushCallBackResponse<XunDaoFtpUpgradeIssueRequest> doPush(XunDaoFtpUpgradeIssuePushRequest xunDaoFtpUpgradeIssuePushRequest) {
        byte[] pushMsg = XunDaoFtpUpgradeIssuePushRequest.packBytes( xunDaoFtpUpgradeIssuePushRequest );
        pushMsg = buildHead(pushMsg, xunDaoFtpUpgradeIssuePushRequest);
        BasePushCallBackResponse<XunDaoFtpUpgradeIssueRequest> basePushCallBackResponse = new BasePushCallBackResponse();
        basePushCallBackResponse.setSerial( xunDaoFtpUpgradeIssuePushRequest.getSerial() );
        boolean flag = pushBusiness.push( pushMsg, xunDaoFtpUpgradeIssuePushRequest.getTradeTypeCode(), xunDaoFtpUpgradeIssuePushRequest.getPileNo(), basePushCallBackResponse, ECommandCode.REMOTE_CHARGE_CODE );
        if (!flag) {
            basePushCallBackResponse.setCode( EPushResponseCode.CONNECT_ERROR );
            return basePushCallBackResponse;
        }
        try {
            basePushCallBackResponse.getCountDownLatch().await( timeout, TimeUnit.MILLISECONDS );
            ChannelResponseCallBackMap.remove( xunDaoFtpUpgradeIssuePushRequest.getTradeTypeCode(), xunDaoFtpUpgradeIssuePushRequest.getPileNo(), xunDaoFtpUpgradeIssuePushRequest.getSerial() );
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error( e.getMessage(), e );
        }
        return basePushCallBackResponse;
    }

    //添加报文头
    private byte[] buildHead(byte[] dataMsg, XunDaoFtpUpgradeIssuePushRequest request) {
        byte[] result = Bytes.concat(new byte[]{0x68}, BytesUtil.intToBytesLittle(184,1));
        result = Bytes.concat(result, BytesUtil.xundaoControlInt2Byte(Integer.parseInt(request.getSerial())));
        //添加类型标识
        result = Bytes.concat(result,BytesUtil.intToBytesLittle(typeCode,1));
        //添加备用
        result = Bytes.concat(result,BytesUtil.intToBytesLittle(0,1));
        //添加传送原因
        result = Bytes.concat(result,new byte[]{0x03,0x00});
        //添加crc
        result = Bytes.concat(result, CRC16Util.getXunDaoCRC(dataMsg));
        //添加记录类型
        result = Bytes.concat(result,BytesUtil.intToBytesLittle(recordType,1));

        result = Bytes.concat(result, dataMsg);

        return result;
    }
}
