package io.ganguo.chat.route.biz.service;

import io.ganguo.chat.route.biz.entity.UserStatus;

/**
 * Created by James on 2015/11/12 0012.
 */
public interface UserOnlineStatusService {

    UserStatus getUserStatus(String account);

    UserStatus insertAndUpdateUserStatus(UserStatus userStatus);
}
