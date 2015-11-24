package io.ganguo.chat.route.client;

import io.ganguo.chat.route.biz.bean.ClientType;
import io.ganguo.chat.route.biz.entity.Message;
import io.ganguo.chat.route.biz.entity.User;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.protocol.Commands;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMResponse;
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
 * @author Tony
 * @createAt Feb 17, 2015
 */
public class ChatClient {

    private final String host;
    private final int port;

    public ChatClient() {
       /* String server = ZkServer.getServerPath();

        if(!StringUtils.isEmpty(server)){
            host = server.split(":")[0];
            port = Integer.parseInt(server.split(":")[1]);
        }else{
            host = "";
            port = 1;
        }*/

        host = "127.0.0.1";
        port = 8989;
    }

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(String username,String password) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            ChannelFuture future = bootstrap.connect(host, port);
            // awaitUninterruptibly() 等待连接成功
            Channel channel = future.awaitUninterruptibly().channel();
            login(channel,username,password);
            sendMessage(channel);
//            future.channel().closeFuture().awaitUninterruptibly();
        } finally {
  //         group.shutdownGracefully();
        }
    }

    public void login(Channel channel,String username,String password) {
        User user = new User();
        user.setClientType(ClientType.WINDOWS.value());
        user.setAccount(username);
        user.setPassword(password);
        user.setcServerIp("127.0.0.1:8989");
        IMResponse resp = new IMResponse();
        Header header = new Header();
        header.setHandlerId(Handlers.USER);
        header.setCommandId(Commands.LOGIN_REQUEST);
        resp.setHeader(header);
        resp.writeEntity(new UserDTO(user));

        channel.writeAndFlush(resp).awaitUninterruptibly();
    }

    public void  sendMessage(Channel channel){

        Message message = new Message();

        message.setFrom(2);
        message.setTo(1);


        message.setFromServer("127.0.0.1:8989");
        message.setToServer("127.0.0.1:9090");
        message.setMessage("hello 2");

        IMResponse resp = new IMResponse();
        Header header = new Header();
        header.setHandlerId(Handlers.MESSAGE);
        header.setCommandId(Commands.USER_MESSAGE_REQUEST);
        resp.setHeader(header);
        resp.writeEntity(new MessageDTO(message));

        channel.writeAndFlush(resp).awaitUninterruptibly();




    }

    public static void main(String[] args) throws Exception {
     //   for (int i = 1; i <= 99999; i++) {
        new ChatClient().run("test2","test2");
        Thread.sleep(10);
    //    }
    }
}
