package com.piles.common.util;

import org.apache.commons.lang3.StringUtils;

public class BytesUtil {
    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。
     */
    public static byte[] intToBytes(int value,int limit)
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
     * int压缩为mdc
     * @param val
     * @return
     */
    public static String int2bcd(int val){

        StringBuilder sb = new StringBuilder();
        do{

            int x = val%10;
            String s = Integer.toBinaryString(x);
            String w = StringUtils.leftPad(s, 4, "0");
            sb.insert(0, w);
            sb.insert(0, " ");
            System.out.println(s);
        }while((val = val/10)>0);
        sb.delete(0,1);

        return sb.toString();
    }

    /**
     * mdc转为int
     * @param bcd
     * @return
     */
    public static int bcd2int(String[] bcd){
        if(bcd==null||bcd.length==0){
            return 0;
        }
        int s = 0;
        int len = bcd.length;
        for(int i =0;i<len;i++){
            int x = Integer.parseInt(bcd[i], 2);
            s+=(x)*Math.pow(10, (len-i-1));
        }
        return s;
    }

    public static void main(String[] args) {
//        byte[] bytes = intToBytes(0x12,2);
////        byte[] bytes = intToBytes(0x12345678,2);
//        int i = bytesToInt(bytes,0);
//        System.out.println(i);
        String s = int2bcd(96);
        int i = bcd2int(StringUtils.split(s, " "));
        System.out.println(i);
        System.out.println(s);
    }
}
