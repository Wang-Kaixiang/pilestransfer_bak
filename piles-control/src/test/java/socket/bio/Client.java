package socket.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Client {

    final static String ADDRESS = "127.0.0.1";
    final static int PORT =9901;

    public static void main(String[] args) {
        byte[] msg= new byte[]{0x1C,0x00,0x02,0x54,0x00,0x17,0x18,0x35,0x02,0x02,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x01,0x00,0x01,0x02,0x00,0x00,0x00,0x04,0x00,0x00,0x00,0x01};
        sendMsgBySocket(msg);
    }

    public static void sendMsgBySocket(byte[] msg){
        OutputStream out = null;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ADDRESS,PORT));
            socket.setKeepAlive(true);
            out = socket.getOutputStream();
            ByteBuffer header = ByteBuffer.allocate(4);
            header.putInt(msg.length);
            byte[] array = header.array();
//            out.write(array);
//            out.write(new byte[4]);
            out.write(msg);
            out.flush();
            InputStream in = socket.getInputStream();
            for(;;) {
                byte[] buff = new byte[4096];
                int readed = in.read(buff);
                if (readed > 0) {
                    String str = new String(buff, 4, readed);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}