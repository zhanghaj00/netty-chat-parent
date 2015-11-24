package io.ganguo.chat.route.handler;

import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.core.handler.IMHandler;
import io.ganguo.chat.core.protocol.Commands;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMRequest;
import io.ganguo.chat.core.transport.IMResponse;
import io.ganguo.chat.route.biz.dto.AckDTO;
import io.ganguo.chat.route.biz.dto.MessageDTO;
import io.ganguo.chat.route.biz.entity.Message;
import io.ganguo.chat.route.session.ClientSession;
import io.ganguo.chat.route.session.ClientSessionManager;
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
        message.setFromServer(connection.getServerConn());
        message.setCreateAt(System.currentTimeMillis());

        ClientSession session = ClientSessionManager.getInstance().get(messageDTO.getMessage().getToServer());
        IMResponse resp = new IMResponse();
        Header header = request.getHeader();
        if (session != null) {
            resp.setHeader(request.getHeader());
            resp.writeEntity(messageDTO);
            session.getConnection().sendResponse(resp);
        } else {
            //TODO 更新用户状态到离线状态  如果服务器重启则状态会没有办法改变

            header.setCommandId(Commands.ERROR_USER_NOT_EXISTS);
            resp.setHeader(request.getHeader());
            connection.sendResponse(resp);
        }

    }

    private void onUserMessageSuccess(IMConnection connection, IMRequest request) {
        AckDTO ack = request.readEntity(AckDTO.class);

        ClientSession session = ClientSessionManager.getInstance().get(ack.getToServer());
        IMResponse resp = new IMResponse();
        resp.setHeader(request.getHeader());
        resp.writeEntity(ack);
        session.getConnection().sendResponse(resp);
    }

}
