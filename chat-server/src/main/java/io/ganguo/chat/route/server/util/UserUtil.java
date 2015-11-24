package io.ganguo.chat.route.server.util;

import io.ganguo.chat.route.biz.entity.UserStatus;
import io.ganguo.chat.route.biz.future.IntFuture;
import io.ganguo.chat.route.biz.service.impl.UserOnlineServiceImpl;
import io.ganguo.chat.route.server.service.UpdateUserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by James on 2015/11/15 0015.
 */

@Component
public class UserUtil {


    @Autowired
    private  static UpdateUserStatusService updateUserStatusService;
    @Autowired
    private  static  UserOnlineServiceImpl userOnlineService;

    public  static void updateUserLoginStatus(UserStatus userStatus){

       // updateUserStatusService = (UpdateUserStatusService) ApplicationContextUtil.getBean("updateUserStatusService");
        IntFuture.exec(updateUserStatusService, userStatus);


    }

    public  UserStatus getUserLoginStatus(String account){
        return userOnlineService.getUserStatus(account);

    }
}
