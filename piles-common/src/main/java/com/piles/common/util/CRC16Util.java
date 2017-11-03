package com.piles.common.util;
/**
 * CRC16相关计算

 * encode: utf-8
 *
 */
public class CRC16Util {

    public static int getCRC(byte[] bytes){
        int crc = 0x0000;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }

        crc &= 0xffff;
        return crc;
    }


    // 测试
    public static void main(String[] args) {
        //68 01 00 00 00 1D 10 00 02 54 84 56 18 35 02 02 00 00 00 00 00 00 00 01 00 01 02 00 00 00 04 00 00 00 01 2B D9
        int crc = CRC16Util.getCRC(new byte[] { 0x01,0x00,0x00,0x00,0x1D,0x10,0x00,0x02,0x54,(byte)0x84,0x56,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01 });
        System.out.println( Integer.toHexString( crc ));
        System.out.println();

    }
}