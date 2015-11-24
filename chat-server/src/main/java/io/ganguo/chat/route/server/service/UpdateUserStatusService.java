package io.ganguo.chat.route.server.service;

import io.ganguo.chat.route.biz.bean.OnlineStatus;
import io.ganguo.chat.route.biz.entity.UserStatus;
import io.ganguo.chat.route.biz.future.FutureService;
import io.ganguo.chat.route.biz.service.impl.UserOnlineServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by James on 2015/11/12 0012.
 */

@Service("updateUserStatusService")
public class UpdateUserStatusService implements FutureService<UserStatus> {

    private Logger logger = LoggerFactory.getLogger(UpdateUserStatusService.class);

    @Resource
    UserOnlineServiceImpl userOnlineService;

    @Override
    public UserStatus handler(Object... args) {

        UserStatus userStatus1 = (UserStatus)args[0];

        UserStatus userStatus = userOnlineService.insertAndUpdateUserStatus(userStatus1);

        return userStatus;

    }

    @Override
    public void onSuccess(Object result, Object... args) {
        logger.debug("success insert ......userStatus");
    }

    @Override
    public void onFailure(Throwable throwable, Object... args) {
        logger.warn("error found on insert");
    }
}
