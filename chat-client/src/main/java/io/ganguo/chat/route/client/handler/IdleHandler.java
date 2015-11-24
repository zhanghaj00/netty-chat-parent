package io.ganguo.chat.route.client.handler;

import io.ganguo.chat.core.connetion.ConnectionManager;
import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.core.protocol.Commands;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMResponse;
import io.ganguo.chat.route.biz.bean.OnlineStatus;
import io.ganguo.chat.route.biz.entity.UserStatus;
import io.ganguo.chat.route.biz.future.IntFuture;
import io.ganguo.chat.route.server.service.UpdateUserStatusService;
import io.ganguo.chat.route.server.util.ApplicationContextUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by James on 2015/11/13 0013.
 */
@Component
public class IdleHandler extends ChannelHandlerAdapter {


    private final Logger logger = LoggerFactory.getLogger(IdleHandler.class);


    private final ConnectionManager mConnectionManager = ConnectionManager.getInstance();

    private  UpdateUserStatusService updateUserStatusService;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        logger.info("idle trigger ....");
        if (evt instanceof IdleStateEvent){
           IdleStateEvent e =  (IdleStateEvent)evt;
                    if (e.state() ==IdleState.READER_IDLE) {
                        logger.debug("read_idle .......");
                        /*IMConnection connection = mConnectionManager.find(ctx.pipeline().lastContext());
                        String account = connection.getAccount();
                      //  updateUserStatusService.handler();
                        if(!StringUtils.isEmpty(account)){
                            UserStatus userStatus = new UserStatus();
                            userStatus.setAccount(account);
                            userStatus.setOnlineStatus(OnlineStatus.OFFLINE.value());

                            updateUserStatusService = (UpdateUserStatusService)ApplicationContextUtil.getBean("updateUserStatusService");

                            IntFuture.exec(updateUserStatusService,userStatus);
                        }
                        ctx.close();*/


                        /*IMResponse resp = new IMResponse();
                        Header header = new Header();
                        header.setHandlerId(Handlers.HEARTBEAT);
                        header.setCommandId(Commands.HEARTBEAT_REQUEST);
                        resp.setHeader(header);
                        ctx.channel().writeAndFlush(resp);*/

                        ctx.close();


                        } else if (e.state() == IdleState.WRITER_IDLE) {
                        logger.debug("write_idle.....");
                        IMResponse resp = new IMResponse();
                        Header header = new Header();
                        header.setHandlerId(Handlers.HEARTBEAT);
                        header.setCommandId(Commands.HEARTBEAT_REQUEST);
                        resp.setHeader(header);
                        ctx.channel().writeAndFlush(resp);
                    } /*else if (e.state()==IdleState.ALL_IDLE) {
                        logger.warn("all idle.......");
                        ctx.channel().close();
                        //业务逻辑。关闭用户状态  this is from account



                    }*/
        }
     //   super.userEventTriggered(ctx, evt);
    }
}
