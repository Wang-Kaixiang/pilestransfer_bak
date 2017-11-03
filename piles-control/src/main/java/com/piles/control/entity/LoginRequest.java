package com.piles.control.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录接口请求实体
 */
@Data
public class LoginRequest implements Serializable
{


    /**
     * 桩编号 8位 BCD
     */
    private int pileNo;
    /**
     * 桩类型 1位 BIN
     */
    private int pileType;
    /**
     * 充电枪数量 1位 BIN
     */
    private int chargeGunCount;
    /**
     * 运营商编码 4位 默认 0 BIN
     */
    private int operatorCode;
    /**
     * 密码 3位 默认000000 BCD
     */
    private int password;
    /**
     * 桩软件版本号 2位 BIN 点号前后各占一个字节。如V1.0表示为0x01 0x00,V1.10表示为0x01 0x0A
     */
    private int pileSoftVersion;
    /**
     * 通信协议版本号 2位 BIN 点号前后各占一个字节。如V1.0表示为0x01 0x00,V1.10表示为0x01 0x0A
     */
    private int protocolVersion;
    /**
     * 计费规则ID 4位 BIN 首次登录填0，后续登录填桩当前正在使用的计费规则ID
     */
    private int billingRuleId;
    /**
     * 计费规则版本号 4位 BIN 首次登录填0，后续登录填桩当前正在使用的计费规则版本号
     */
    private int billingRuleVersion;


}
