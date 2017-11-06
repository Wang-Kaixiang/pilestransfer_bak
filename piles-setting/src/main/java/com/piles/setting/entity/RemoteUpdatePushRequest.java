package com.piles.setting.entity;

import com.google.common.primitives.Bytes;
import com.piles.common.entity.BasePushRequest;
import com.piles.common.util.BytesUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 远程升级 运营管理系统  充电桩
 */
@Data
public class RemoteUpdatePushRequest extends BasePushRequest implements Serializable
{
    //软件版本号	BIN	2	点号前后各占一个字节。如V1.0表示为0x01 0x00,V1.10表示为0x01 0x0A
    private String softVersion;
    //通信协议版本号	BIN	2	格式同上
    private String protocolVersion;
    //升级文件MD5	ASCII	32	升级文件的MD5，用于校验文件是否完整
    private String md5;
    //升级文件下载地址长度	BIN	2	可变
    private int urlLen;
    //升级文件下载地址	ASCII	N	http链接地址
    private String url;

    /**
     * 解析报文并封装request体
     * @param msg
     * @return
     */
    public static byte[] packBytes(RemoteUpdatePushRequest request){
        String softVersion = request.getSoftVersion();
        String protocolVersion = request.getProtocolVersion();
        String md5 = request.getMd5();
        String url = request.getUrl();

        //TODO 待处理
        byte[] softVersionBytes = md5.getBytes();
        byte[] protocolVersionBytes = md5.getBytes();
        byte[] md5Bytes = md5.getBytes();
        if(url!=null){
            url = "";
        }
        int urlLen = url.length();
        byte[] urlLenBytes = BytesUtil.intToBytes(urlLen, 2);
        byte[] urlBytes = url.getBytes();
        return Bytes.concat(softVersionBytes,protocolVersionBytes,md5Bytes,urlLenBytes,urlBytes);
    }


    public static void main(String[] args) {

    }



}
