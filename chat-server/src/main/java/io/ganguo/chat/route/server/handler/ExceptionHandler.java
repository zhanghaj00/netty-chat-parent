package io.ganguo.chat.route.server.handler;

import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.core.handler.IMHandler;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.route.biz.bean.OnlineStatus;
import io.ganguo.chat.route.biz.entity.UserStatus;
import io.ganguo.chat.route.biz.future.IntFuture;
import io.ganguo.chat.route.biz.service.impl.UserOnlineServiceImpl;
import io.ganguo.chat.route.server.service.UpdateUserStatusService;
import io.ganguo.chat.route.server.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


/**
 * Created by James on 2015/11/24 0024.
 */
@Component
public class ExceptionHandler extends IMHandler {

    @Autowired
    private  UpdateUserStatusService updateUserStatusService;
    @Resource
    UserOnlineServiceImpl userOnlineService;

    @Override
    public short getId() {
        return Handlers.EXCEPTION;
    }

    @Override
    public void dispatch(IMConnection connection, Object data) {

      //  IMConnection connection = mConnectionManager.find(ctx.pipeline().lastContext());
        String account = connection.getAccount();
        //  updateUserStatusService.handler();
        if(!StringUtils.isEmpty(account)){
            UserStatus userStatus = userOnlineService.getUserStatus(account);
            userStatus.setAccount(account);
            userStatus.setOnlineStatus(OnlineStatus.OFFLINE.value());
            IntFuture.exec(updateUserStatusService,userStatus);
        }
    }
}
