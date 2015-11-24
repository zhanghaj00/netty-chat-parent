package io.ganguo.chat.route.session;

import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.route.biz.bean.Presence;
import io.ganguo.chat.route.event.EventDispatcherManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tony on 2/21/15.
 */
public class ClientSessionManager {

    private final Map<String, ClientSession> mSessions;
    private static ClientSessionManager mInstance;

    private ClientSessionManager() {
        mSessions = new ConcurrentHashMap<String, ClientSession>();
    }

    public static ClientSessionManager getInstance() {
        if (mInstance == null) {
            mInstance = new ClientSessionManager();
        }
        return mInstance;
    }

    public void add(final ClientSession session) {
        mSessions.put(session.getUin(), session);

        EventDispatcherManager.getPresenceEventDispatcher().availableSession(session);

        session.getConnection().registerCloseListener(new IMConnection.ConnectionCloseListener() {
            @Override
            public void onClosed(IMConnection connection) {
                mSessions.remove(session.getUin());
                session.getPresence().setMode(Presence.Mode.UNAVAILABLE.value());
                EventDispatcherManager.getPresenceEventDispatcher().unavailableSession(session);
            }
        });
    }

    public ClientSession get(String uin) {
        return mSessions.get(uin);
    }

    public Map<String, ClientSession> sessions() {
        return mSessions;
    }

}
