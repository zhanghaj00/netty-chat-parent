package io.ganguo.chat.route.server;

import io.ganguo.chat.route.server.config.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * Chat Server
 */
@Component
public class ChatServer {

    private final int PORT;

    public ChatServer() {
        PORT = Constant.PORT1.getValue();
    }

    public ChatServer(int port) {
        PORT = port;
    }

    /**
     * netty
     *
     * @throws Exception
     */
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer());

            bootstrap
                    .bind(PORT)
                    .sync().channel()
                    .closeFuture().sync();



            //TODO  在ZK上注册本服务
           // System.out.print("check.................");
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
