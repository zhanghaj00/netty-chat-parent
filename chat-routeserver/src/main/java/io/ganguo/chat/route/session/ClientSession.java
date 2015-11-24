package io.ganguo.chat.route.session;

import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.route.biz.bean.Presence;
import io.ganguo.chat.route.biz.entity.Login;

/**
 * Created by Tony on 2/21/15.
 */
public class ClientSession {

    private IMConnection mConnection;
    private Presence mPresence;
   // private Login mLogin;

    public ClientSession(String serverUrl, IMConnection connection) {
        mConnection = connection;
        mPresence = new Presence();
       // mPresence.setUin(serverUrl);
        mPresence.setMode(Presence.Mode.AVAILABLE.value());
        // bind Uin
        mConnection.setServerConn(serverUrl);
    }

    public String getUin() {
        return mConnection.getServerConn();
    }

    public IMConnection getConnection() {
        return mConnection;
    }

    public void setConnection(IMConnection mConnection) {
        this.mConnection = mConnection;
    }

    public Presence getPresence() {
        return mPresence;
    }

    public void setPresence(Presence presence) {
        mPresence = presence;
    }

  /*  public Login getLogin() {
        return mLogin;
    }

    public void setLogin(Login login) {
        mLogin = login;
    }*/
}
