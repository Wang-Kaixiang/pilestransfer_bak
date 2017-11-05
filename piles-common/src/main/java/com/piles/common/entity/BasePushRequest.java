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
}
