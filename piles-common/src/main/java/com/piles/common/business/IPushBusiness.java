package com.piles.common.business;

/**
 * 推送消息业务
 */
public interface IPushBusiness {
    /**
     * 推送消息
     * @param msg 消息体
     * @param pileNo 桩编号
     * @return
     */
    boolean push(byte[] msg,String pileNo);
}
