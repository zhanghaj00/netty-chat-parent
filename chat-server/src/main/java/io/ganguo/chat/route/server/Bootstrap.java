package io.ganguo.chat.route.server;

import io.ganguo.chat.route.server.config.Constant;
import io.ganguo.chat.route.server.cpooled.S2RChannelFactory;
import io.ganguo.chat.route.server.cpooled.TempChannel;
import io.ganguo.chat.route.server.zookeeper.ZkClient4Server;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * startup chat server
 * <p/>
 * Created by Tony on 2/18/15.
 */
@Configuration
@ComponentScan("io.ganguo.chat")
public class Bootstrap {
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        ChatServer chatServer = ChatContext.getBean(ChatServer.class);
        try {
            //在ZK 上注册自己的服务
           ZkClient4Server.provideServer("127.0.0.1:2181","127.0.0.1:"+ Constant.PORT1.getValue());
         ///   Channel channel =  S2RChannelFactory.getS2RChannelInstance().getChannel();
            Channel channel = TempChannel.getInstance().getChannel();
 //          S2RChannelFactory.getS2RChannelInstance().returnChannel(channel);
            //链接总的ROUTE server 集群 并且返回 channel 或者实现一个channel 池
            //或者创建多个对服务器的链接 用连接池来管理

            chatServer.run();






        } catch (Exception e) {
            logger.error("startup ChatServer error!!!", e);
        }
    }

}
