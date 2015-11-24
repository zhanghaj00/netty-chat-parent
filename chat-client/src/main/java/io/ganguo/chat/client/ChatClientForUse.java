package io.ganguo.chat.client;

import io.ganguo.chat.core.protocol.Commands;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMResponse;
import io.ganguo.chat.route.biz.bean.ClientType;
import io.ganguo.chat.route.biz.entity.Message;
import io.ganguo.chat.route.biz.entity.User;
import io.ganguo.chat.route.client.ChatClientInitializer;
import io.ganguo.chat.route.biz.dto.MessageDTO;
import io.ganguo.chat.route.biz.dto.UserDTO;
import io.ganguo.chat.zookeeper.ZkServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.util.StringUtils;

/**
 * Created by James on 2015/11/15 0015.
 * 单利模式  一个用户一个链接
 */
public final class ChatClientForUse {


    private final String host;
    private final int port;

    public ChatClientForUse() {

        String server = ZkServer.getServerPath();

        if(!StringUtils.isEmpty(server)){
            host = server.split(":")[0];
            port = Integer.parseInt(server.split(":")[1]);
        }else{
            host = "";
            port = 1;
        }
    }

    public ChatClientForUse(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            ChannelFuture future = bootstrap.connect(host, port);
            // awaitUninterruptibly() 等待连接成功
            Channel channel = future.awaitUninterruptibly().channel();
            login(channel);
            //       sendMessage(channel);
//            future.channel().closeFuture().awaitUninterruptibly();
        } finally {
            //         group.shutdownGracefully();
        }
    }

   public final Channel connectAndLoginServer(User user){

       EventLoopGroup group = new NioEventLoopGroup();
       Channel channel = null;
       try {
           Bootstrap bootstrap = new Bootstrap()
                   .group(group)
                   .channel(NioSocketChannel.class)
                   .handler(new ChatClientInitializer());

           ChannelFuture future = bootstrap.connect(host, port);
           // awaitUninterruptibly() 等待连接成功
           channel = future.awaitUninterruptibly().channel();
           login(user,channel);

       } finally{

       }
       return channel;
   }

    private  void login(Channel channel) {
        User user = new User();
        user.setClientType(ClientType.WINDOWS.value());
        user.setAccount("test1");
        user.setPassword("test1");

        IMResponse resp = new IMResponse();
        Header header = new Header();
        header.setHandlerId(Handlers.USER);
        header.setCommandId(Commands.LOGIN_REQUEST);
        resp.setHeader(header);
        resp.writeEntity(new UserDTO(user));

        channel.writeAndFlush(resp).awaitUninterruptibly();
    }

    private void login(User user,Channel channel) {

        user.setcServerIp(host+":"+port);
        IMResponse resp = new IMResponse();
        Header header = new Header();
        header.setHandlerId(Handlers.USER);
        header.setCommandId(Commands.LOGIN_REQUEST);
        resp.setHeader(header);
        resp.writeEntity(new UserDTO(user));

        channel.writeAndFlush(resp).awaitUninterruptibly();
    }

    public void  sendMessage(Channel channel,Message message){

        /*Message message = new Message();

        message.setFrom(2);
        message.setTo(1);
        message.setMessage("hello 2");*/

        IMResponse resp = new IMResponse();
        Header header = new Header();
        header.setHandlerId(Handlers.MESSAGE);
        header.setCommandId(Commands.USER_MESSAGE_REQUEST);
        resp.setHeader(header);
        resp.writeEntity(new MessageDTO(message));

        channel.writeAndFlush(resp).awaitUninterruptibly();




    }
}
