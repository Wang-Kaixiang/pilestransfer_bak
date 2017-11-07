package com.piles.common.util;

/**
 * CRC16相关计算
 * <p>
 * encode: utf-8
 */
public class CRC16Util {

    public static int getCRC(byte[] bytes) {
        int crc = 0x0000;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }

        crc &= 0xffff;
        return crc;
    }

    /**
     * 数据校验
     *
     * @param msg
     * @return
     */
    public static boolean checkMsg(byte[] msg) {
        //起始位必须是0x68
        if (0x68 != msg[0]) {
            return false;
        }
        //长度小于3 抛弃
        if (msg.length > 3) {
            byte[] temp = new byte[msg.length - 3];
            System.arraycopy( msg, 1, temp, 0, temp.length );
            int crc = getCRC( temp );
            if (Integer.toHexString( crc ).equalsIgnoreCase( Integer.toHexString( Byte.toUnsignedInt( msg[msg.length-2] ) ) +Integer.toHexString( Byte.toUnsignedInt( msg[msg.length-1] ) ))) {
                return true;
            }
        }
        return false;

    }

    // 测试
    public static void main(String[] args) {
//        0x2b

//        String s=Integer.toHexString( Byte.toUnsignedInt( (byte)0x2b ) );
//        System.out.println(s);
        //68 01 00 00 00 1D 10 00 02 54 84 56 18 35 02 02 00 00 00 00 00 00 00 01 00 01 02 00 00 00 04 00 00 00 01 2B D9
        byte[] temp=new byte[]{(byte)0x8E,0x00,0x00,0x00,0x01,0x01};
        int crc = CRC16Util.getCRC(temp );
        System.out.println( Integer.toHexString( crc ) );

        System.out.println(checkMsg( temp ));
//        System.out.println();

    }
}