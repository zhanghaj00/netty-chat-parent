package io.ganguo.chat.route.biz.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by James on 2015/11/12 0012.
 */

@Document
public class UserStatus extends BaseEntity {

    @Indexed(unique = true)
    private String account;
    private byte onlineStatus;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public byte getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(byte onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
