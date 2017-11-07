package socket;

import com.alibaba.fastjson.JSON;
import com.piles.common.util.ChannelMap;
import com.piles.control.entity.RemoteClosePushRequest;
import com.piles.control.service.IRemoteClosePushService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerTest {
    private static final String SRPING_ROOT_XML = "classpath:application.xml";


    private static final Logger LOGGER = LoggerFactory.getLogger(ServerTest.class);

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(SRPING_ROOT_XML)) {
            context.registerShutdownHook();
            context.start();


            LOGGER.info("启动成功");

            while (true){
                Channel channel=ChannelMap.getChannel("1000025484561835");
                if (null!=channel){
                    IRemoteClosePushService service= context.getBean( IRemoteClosePushService.class );
                    RemoteClosePushRequest remoteClosePushRequest= new RemoteClosePushRequest();
                    remoteClosePushRequest.setGunNo( 1 );
                    remoteClosePushRequest.setOrderNo( 123456L );
                    remoteClosePushRequest.setPileNo( "1000025484561835" );
                    remoteClosePushRequest.setSerial( "0000" );
                    System.out.println("远程结束充电返回信息"+ JSON.toJSONString(  service.doPush( remoteClosePushRequest)));

                }
                Thread.sleep(10000L);
            }

        } catch (Exception e) {
            LOGGER.error("Spring 启动错误", e);
        }

    }

}
