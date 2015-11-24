package io.ganguo.chat.route.biz.service.impl;

import io.ganguo.chat.route.biz.entity.UserStatus;
import io.ganguo.chat.route.biz.repository.UserStatusRepository;
import io.ganguo.chat.route.biz.service.UserOnlineStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by James on 2015/11/12 0012.
 */

@Service
public class UserOnlineServiceImpl implements UserOnlineStatusService{

    @Autowired
    UserStatusRepository userStatusRepository;

    @Override
    public UserStatus getUserStatus(String account) {

        return userStatusRepository.findByAccount(account);
    }

    @Override
    public UserStatus insertAndUpdateUserStatus(UserStatus userStatus) {


        UserStatus userStatus1 = userStatusRepository.findByAccount(userStatus.getAccount());

        if(null == userStatus1){
            userStatus1 = new UserStatus();
        }
        userStatus1.setAccount(userStatus.getAccount());
        userStatus1.setOnlineStatus(userStatus.getOnlineStatus());

        return  userStatusRepository.save(userStatus1);
    }


}
