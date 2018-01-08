package com.piles.setting.service;

import com.piles.common.entity.BasePushCallBackResponse;
import com.piles.setting.entity.XunDaoFtpUpgradeIssuePushRequest;
import com.piles.setting.entity.XunDaoFtpUpgradeIssueRequest;

/**
 * Created by zhanglizhi on 2018/1/8.
 */
public interface IXunDaoFtpUpgradeIssueService {

    /**
     * 下发ftp升级程序数据接口
     * @param xunDaoFtpUpgradeIssuePushRequest
     * @return 请求信息
     */
    BasePushCallBackResponse<XunDaoFtpUpgradeIssueRequest> doPush(XunDaoFtpUpgradeIssuePushRequest xunDaoFtpUpgradeIssuePushRequest);

}
