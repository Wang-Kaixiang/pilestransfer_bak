package com.piles.common.Service;


import com.piles.common.entity.BasePushRequest;

/**
 * 定义运营系统推送到本server端的接收处理服务
 * @param <C>
 */
public interface IPushService<C extends BasePushRequest> {

    boolean doPush(C Var);
}
