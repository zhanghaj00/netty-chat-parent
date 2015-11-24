package io.ganguo.chat.route.server.handler;

import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.core.handler.IMHandler;
import io.ganguo.chat.core.protocol.Commands;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMRequest;
import io.ganguo.chat.core.transport.IMResponse;
import io.ganguo.chat.route.biz.entity.Message;
import io.ganguo.chat.route.biz.dto.AckDTO;
import io.ganguo.chat.route.biz.dto.MessageDTO;
import io.ganguo.chat.route.server.cpooled.S2RChannelFactory;
import io.ganguo.chat.route.server.cpooled.TempChannel;
import io.ganguo.chat.route.server.session.ClientSession;
import io.ganguo.chat.route.server.session.ClientSessionManager;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * Created by Tony on 2/20/15.
 */
@Component
public class MessageHandler extends IMHandler<IMRequest> {

    @Override
    public short getId() {
        return Handlers.MESSAGE;
    }

    @Override
    public void dispatch(IMConnection connection, IMRequest request) {
        Header header = request.getHeader();
        switch (header.getCommandId()) {
            case Commands.USER_MESSAGE_REQUEST:
                sendUserMessage(connection, request);
                break;
            case Commands.USER_MESSAGE_SUCCESS:
                onUserMessageSuccess(connection, request);
                break;
            default:
                connection.close();
                break;
        }
    }

    private void sendUserMessage(IMConnection connection, IMRequest request) {
        MessageDTO messageDTO = request.readEntity(MessageDTO.class);
        Message message = messageDTO.getMessage();
        message.setFrom(message.getFrom());
        message.setCreateAt(System.currentTimeMillis());

        ClientSession session = ClientSessionManager.getInstance().get(messageDTO.getTo());
        IMResponse resp = new IMResponse();
        Header header = request.getHeader();
        if (session != null) {
            resp.setHeader(request.getHeader());
            resp.writeEntity(messageDTO);
            session.getConnection().sendResponse(resp);
        } else {
            //TODO 更新用户状态到离线状态  如果服务器重启则状态会没有办法改变
            //分发到routeServer

         //   Channel channel =  S2RChannelFactory.getS2RChannelInstance().getChannel();
            Channel channel = TempChannel.getInstance().getChannel();;
            resp.setHeader(request.getHeader());
            resp.writeEntity(messageDTO);

            channel.writeAndFlush(resp);

         //   S2RChannelFactory.getS2RChannelInstance().returnChannel(channel);
           /* header.setCommandId(Commands.ERROR_USER_NOT_EXISTS);
            resp.setHeader(request.getHeader());
            connection.sendResponse(resp);*/
        }

    }

    private void onUserMessageSuccess(IMConnection connection, IMRequest request) {
        AckDTO ack = request.readEntity(AckDTO.class);

        ClientSession session = ClientSessionManager.getInstance().get(ack.getTo());
        IMResponse resp = new IMResponse();
        if(session !=null){


            resp.setHeader(request.getHeader());
            resp.writeEntity(ack);
            session.getConnection().sendResponse(resp);

        }else{

         //   Channel channel =  S2RChannelFactory.getS2RChannelInstance().getChannel();
            Channel channel = TempChannel.getInstance().getChannel();
            resp.setHeader(request.getHeader());
            resp.writeEntity(ack);

            channel.writeAndFlush(resp);
      //      S2RChannelFactory.getS2RChannelInstance().returnChannel(channel);

        }



    }

}
