package com.piles.common.entity;

import lombok.Data;

/**
 * push 信息基础request
 */
@Data
public class BasePushRequest {
    /**
     * 默认必填充电桩编号
     */
    private String pileNo;

    /**
     * 默认必填流水号
     */
    private String serial;
    /**
     * 对应厂商类型  1:蔚景 2: 循道
     */
    private int tradeTypeCode;
}
