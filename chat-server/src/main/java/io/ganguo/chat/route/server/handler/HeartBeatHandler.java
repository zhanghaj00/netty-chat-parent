package io.ganguo.chat.route.server.handler;

import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.core.handler.IMHandler;
import io.ganguo.chat.core.protocol.Commands;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMRequest;
import io.ganguo.chat.core.transport.IMResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by James on 2015/11/14 0014.
 */
@Component
public class HeartBeatHandler extends IMHandler<IMRequest> {


    private Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Override
    public short getId() {
        return Handlers.HEARTBEAT;
    }

    @Override
    public void dispatch(IMConnection connection, IMRequest request) {
        Header header = request.getHeader();
        switch (header.getCommandId()) {
            case Commands.HEARTBEAT_REQUEST:
                sendHeartBeatRequest(connection,request);
                break;
            default:
                break;


        }
    }

    private void sendHeartBeatRequest(IMConnection connection, IMRequest request){
       /* IMResponse resp = new IMResponse();
        Header header = new Header();
        header.setHandlerId(Handlers.HEARTBEAT);
        header.setCommandId(Commands.HEARTBEAT_REQUEST);
        resp.setHeader(header);
        connection.sendResponse(resp);*/
        // do nothing
        logger.warn("heartbeat ......");


    }

}
