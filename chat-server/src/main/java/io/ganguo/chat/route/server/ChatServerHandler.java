package io.ganguo.chat.route.server;

import io.ganguo.chat.core.connetion.ConnectionManager;
import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.core.handler.IMHandler;
import io.ganguo.chat.core.handler.IMHandlerManager;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMRequest;
import io.ganguo.chat.route.biz.bean.OnlineStatus;
import io.ganguo.chat.route.biz.entity.User;
import io.ganguo.chat.route.biz.entity.UserStatus;
import io.ganguo.chat.route.biz.future.IntFuture;
import io.ganguo.chat.route.server.service.UpdateUserStatusService;
import io.ganguo.chat.route.server.util.ApplicationContextUtil;
import io.ganguo.chat.route.server.util.UserUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ChatServerHandler extends SimpleChannelInboundHandler<IMRequest> {

    private Logger logger = LoggerFactory.getLogger(ChatServerHandler.class);

    private final ConnectionManager mConnectionManager = ConnectionManager.getInstance();

    private  UpdateUserStatusService updateUserStatusService;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        mConnectionManager.create(ctx);

        logger.warn("handlerAdded " + mConnectionManager.count());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);

        mConnectionManager.remove(ctx);

        logger.warn("handlerRemoved " + mConnectionManager.count());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, IMRequest request) throws Exception {
        logger.warn("messageReceived header:" + request.getHeader());


        //TODO 集群环境下 通过总路由和子路由的方式来查找 conn
        IMConnection conn = mConnectionManager.find(ctx);
        Header header = request.getHeader();

        IMHandler handler = IMHandlerManager.getInstance().find(header.getHandlerId());
        if (handler != null) {
            handler.dispatch(conn, request);
        } else {
            logger.warn("Not found handlerId: " + header.getHandlerId());
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        IMHandler handler = IMHandlerManager.getInstance().find(Handlers.EXCEPTION);

        IMConnection connection = mConnectionManager.find(ctx.pipeline().lastContext());
        if(handler != null){
            handler.dispatch(connection,null);
        }
        if (this == ctx.pipeline().last()) {
            logger.warn(
                    "EXCEPTION, please implement " + getClass().getName() +
                            ".exceptionCaught() for proper handling.", cause.getCause());
        }
    }
}
