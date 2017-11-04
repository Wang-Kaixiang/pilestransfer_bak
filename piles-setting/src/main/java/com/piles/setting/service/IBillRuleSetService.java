package com.piles.setting.service;

import com.piles.setting.entity.BillRuleSetRequest;

/**
 * 计费规则设置
 */
public interface IBillRuleSetService {
    /**
     * 计费规则设置 远程推送
     * @param billRuleSetRequest 请求体
     * @return 验证成功返回true，失败返回false
     */
    boolean billRuleSet(BillRuleSetRequest billRuleSetRequest);
}
