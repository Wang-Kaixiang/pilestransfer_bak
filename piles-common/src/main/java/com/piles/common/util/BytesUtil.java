package com.piles.common.util;

import org.apache.commons.lang3.StringUtils;
import sun.jvm.hotspot.runtime.Bytes;

import java.math.BigInteger;

public class BytesUtil {
    /**
     * 将int数值转换为占两个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     */
    public static byte[] intToBytes(int value)
    {
        //limit 传入2
        byte[] src = new byte[2];
        src[0] = (byte) ((value>>8)&0xFF);
        src[1] = (byte) (value & 0xFF);
//        byte[] src = new byte[4];
//        src[0] = (byte) ((value>>24) & 0xFF);
//        src[1] = (byte) ((value>>16)& 0xFF);
//        src[2] = (byte) ((value>>8)&0xFF);
//        src[3] = (byte) (value & 0xFF);
        return src;
    }
    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     */
    public static byte[] intToBytes4(int value)
    {
        //limit 传入2
//        byte[] src = new byte[2];
//        src[0] = (byte) ((value>>8)&0xFF);
//        src[1] = (byte) (value & 0xFF);
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }


    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) (  ((src[offset] & 0xFF)<<8)
                |(src[offset+1] & 0xFF));
//        value = (int) ( ((src[offset] & 0xFF)<<24)
//                |((src[offset+1] & 0xFF)<<16)
//                |((src[offset+2] & 0xFF)<<8)
//                |(src[offset+3] & 0xFF));
        return value;
    }

    /**
     * 从一个byte数组中拷贝一部分出来
     *
     * @param oriBytes
     * @param startIndex
     * @param length
     * @return
     */
    public static byte[] copyBytes(byte[] oriBytes, int startIndex, int length) {
        int endIndex = startIndex + length;

        byte[] bts = new byte[length];

        int index = 0;
        for (int i = 0; i < oriBytes.length; i++) {
            if (i >= startIndex && i < endIndex) {
                bts[index] = oriBytes[i];
                index++;
            }
        }

        return bts;
    }




    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }
    /** *//**
     * @函数功能: BCD码转为10进制串(阿拉伯数据)
     * @输入参数: BCD码
     * @输出结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes){
        StringBuffer temp=new StringBuffer(bytes.length*2);

        for(int i=0;i<bytes.length;i++){
            temp.append((byte)((bytes[i]& 0xf0)>>>4));
            temp.append((byte)(bytes[i]& 0x0f));
        }
        return temp.toString();
    }

    /**
     * @函数功能: 10进制串转为BCD码
     * @输入参数: 10进制串
     * @输出结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length()/2; p++) {
            if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            }else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    public static void main(String[] args) {
//        byte[] bytes = intToBytes(0x12,2);
////        byte[] bytes = intToBytes(0x12345678,2);
//        int i = bytesToInt(bytes,0);
//        System.out.println(i);

//        System.out.println(i);
//        System.out.println(s);
        byte[] temp=str2Bcd("1000025484561835");
        System.out.println(bcd2Str(new byte[]{temp[7]}));
//        System.out.println(Integer.toHexString(Byte.toUnsignedInt(temp[1])).toUpperCase());
    }
}
