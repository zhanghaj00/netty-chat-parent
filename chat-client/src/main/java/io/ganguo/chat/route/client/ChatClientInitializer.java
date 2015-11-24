package io.ganguo.chat.route.client;

import io.ganguo.chat.route.client.handler.HeartBeatHandler;
import io.ganguo.chat.route.client.handler.IdleHandler;
import io.ganguo.chat.route.client.handler.MessageHandler;
import io.ganguo.chat.route.client.handler.UserHandler;
import io.ganguo.chat.core.codec.PacketDecoder;
import io.ganguo.chat.core.codec.PacketEncoder;
import io.ganguo.chat.core.handler.IMHandlerManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("decoder", new PacketDecoder(8192, 0, 4));
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("idleHandler",new IdleStateHandler(30,25,60));
        pipeline.addLast("heartbeatHandler",new IdleHandler());
        pipeline.addLast("handler", new ChatClientHandler());

        initIMHandler();
    }

    private void initIMHandler() {
        IMHandlerManager.getInstance().register(UserHandler.class);
        IMHandlerManager.getInstance().register(MessageHandler.class);
        IMHandlerManager.getInstance().register(HeartBeatHandler.class);
    }
}
