package com.piles.setting.business.impl;

import com.piles.common.business.IBusiness;
import com.piles.common.util.BytesUtil;
import com.piles.common.util.ChannelResponseCallBackMap;
import com.piles.setting.entity.XunDaoFtpUpgradeIssueRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 循道 FTP升级程序下行数据(后台-->充电桩 交直流共用)
 */
@Slf4j
@Service("xunDaoFtpUpgradeIssueBusiness")
public class XunDaoFtpUpgradeIssueBusinessImpl implements IBusiness {


    @Override
    public byte[] process(byte[] msg, Channel incoming) {

        //TODO 流水号
        int order = 0;
        log.info( "接收到循道充电桩是否升级报文" );
        byte[] dataBytes = BytesUtil.copyBytes(msg, 13, (msg.length - 13));
        //依照报文体规则解析报文
        XunDaoFtpUpgradeIssueRequest ftpUpgradeIssueRequest = XunDaoFtpUpgradeIssueRequest.packEntity( dataBytes );
        //调用底层接口
        ChannelResponseCallBackMap.callBack( incoming, String.valueOf( order ), ftpUpgradeIssueRequest );
        return null;
    }
}
