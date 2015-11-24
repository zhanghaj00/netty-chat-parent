package io.ganguo.chat.route.biz.bean;

/**
 * Created by James on 2015/11/12 0012.
 */
public enum OnlineStatus {

    //在线1 离线2  位置状态3
    ONLINE(1), OFFLINE(2), UNKNOUWN(3);

    private byte mValue = 1;

    public byte value() {
        return mValue;
    }

    OnlineStatus(int value) {
        mValue = (byte) value;
    }

    public static OnlineStatus valueOfRaw(byte value) {
        for (OnlineStatus gender : OnlineStatus.values()) {
            if (gender.value() == value) {
                return gender;
            }
        }
        return UNKNOUWN;
    }
}
