package io.ganguo.chat.route.server;

import io.ganguo.chat.core.codec.PacketDecoder;
import io.ganguo.chat.core.codec.PacketEncoder;
import io.ganguo.chat.core.handler.IMHandler;
import io.ganguo.chat.core.handler.IMHandlerManager;
import io.ganguo.chat.route.server.handler.IdleHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;
import java.util.Map;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("decoder", new PacketDecoder(8192, 0, 4));
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("heartbeat",new IdleStateHandler(30,25,30,TimeUnit.SECONDS));
        pipeline.addLast("logoutHander",new IdleHandler());
        pipeline.addLast("handler", new ChatServerHandler());

        initIMHandler();
    }

    private void initIMHandler() {
        // register all handlers
        Map<String, IMHandler> handlers = ChatContext.getBeansOfType(IMHandler.class);
        for (String key : handlers.keySet()) {
            IMHandler handler = handlers.get(key);
            IMHandlerManager.getInstance().register(handler);
        }
    }
}
