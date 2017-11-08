package socket;

import com.alibaba.fastjson.JSON;
import com.piles.common.util.ChannelMap;
import com.piles.control.entity.RemoteClosePushRequest;
import com.piles.control.entity.RemoteStartPushRequest;
import com.piles.control.service.IRemoteClosePushService;
import com.piles.control.service.IRemoteStartPushService;
import com.piles.setting.entity.BillRuleSetPushRequest;
import com.piles.setting.entity.RebootPushRequest;
import com.piles.setting.entity.RemoteUpdatePushRequest;
import com.piles.setting.entity.VerifyTimePushRequest;
import com.piles.setting.service.IBillRuleSetPushService;
import com.piles.setting.service.IRebootPushService;
import com.piles.setting.service.IRemoteUpdatePushService;
import com.piles.setting.service.IVerifyTimePushService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ServerTest {
    private static final String SRPING_ROOT_XML = "classpath:application-setting.xml";


    private static final Logger LOGGER = LoggerFactory.getLogger( ServerTest.class );

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext( SRPING_ROOT_XML )) {
            context.registerShutdownHook();
            context.start();


            LOGGER.info( "启动成功" );

            while (true) {
                Channel channel = ChannelMap.getChannel( "1000025484561835" );
                if (null != channel) {

//
//                    IVerifyTimePushService service = context.getBean( IVerifyTimePushService.class );
//                    VerifyTimePushRequest remoteClosePushRequest = new VerifyTimePushRequest();
//                    remoteClosePushRequest.setPileNo( "1000025484561835" );
//                    remoteClosePushRequest.setSerial( "0000" );
//                    remoteClosePushRequest.setServerTime( "20171107183025" );
//                    System.out.println( "校时返回信息" + JSON.toJSONString( service.doPush( remoteClosePushRequest ) ) );

//                    IRebootPushService service = context.getBean( IRebootPushService.class );
//                    RebootPushRequest remoteClosePushRequest = new RebootPushRequest();
//                    remoteClosePushRequest.setPileNo( "1000025484561835" );
//                    remoteClosePushRequest.setSerial( "0000" );
//                    System.out.println( "重启返回信息" + JSON.toJSONString( service.doPush( remoteClosePushRequest ) ) );

                    IRemoteUpdatePushService service = context.getBean( IRemoteUpdatePushService.class );
                    RemoteUpdatePushRequest remoteClosePushRequest = new RemoteUpdatePushRequest();
                    remoteClosePushRequest.setPileNo( "1000025484561835" );
                    remoteClosePushRequest.setSerial( "0000" );
                    remoteClosePushRequest.setMd5( "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" );
                    remoteClosePushRequest.setProtocolVersion( "V1.0" );
                    remoteClosePushRequest.setSoftVersion( "V1.10" );
                    remoteClosePushRequest.setUrl( "http://essrus" );
                    remoteClosePushRequest.setUrlLen( 13 );
                    System.out.println( "远程升级返回信息" + JSON.toJSONString( service.doPush( remoteClosePushRequest ) ) );


//

//                    IRemoteClosePushService service = context.getBean( IRemoteClosePushService.class );
//                    RemoteClosePushRequest remoteClosePushRequest = new RemoteClosePushRequest();
//                    remoteClosePushRequest.setGunNo( 1 );
//                    remoteClosePushRequest.setOrderNo( 123456L );
//                    remoteClosePushRequest.setPileNo( "1000025484561835" );
//                    remoteClosePushRequest.setSerial( "0000" );
//                    System.out.println( "远程结束充电返回信息" + JSON.toJSONString( service.doPush( remoteClosePushRequest ) ) );

//                    IRemoteStartPushService service= context.getBean( IRemoteStartPushService.class );
//                    RemoteStartPushRequest remoteClosePushRequest= new RemoteStartPushRequest();
//                    remoteClosePushRequest.setGunNo( 1 );
//                    remoteClosePushRequest.setOrderNo( 123456L );
//                    remoteClosePushRequest.setPileNo( "1000025484561835" );
//                    remoteClosePushRequest.setSerial( "0000" );
//                    remoteClosePushRequest.setChargeData( new BigDecimal( 1 ) );
//                    remoteClosePushRequest.setChargeModel( 1 );
//                    remoteClosePushRequest.setChargeStopCode( "646444" );
//                    System.out.println("远程启动充电返回信息"+ JSON.toJSONString(  service.doPush( remoteClosePushRequest)));

                }
                Thread.sleep( 10000L );
            }

        } catch (Exception e) {
            LOGGER.error( "Spring 启动错误", e );
        }

    }

}
