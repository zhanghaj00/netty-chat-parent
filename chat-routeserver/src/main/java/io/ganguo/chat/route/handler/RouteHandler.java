package io.ganguo.chat.route.handler;

import io.ganguo.chat.core.connetion.IMConnection;
import io.ganguo.chat.core.handler.IMHandler;
import io.ganguo.chat.core.protocol.Commands;
import io.ganguo.chat.core.protocol.Handlers;
import io.ganguo.chat.core.transport.Header;
import io.ganguo.chat.core.transport.IMRequest;
import io.ganguo.chat.core.transport.IMResponse;
import io.ganguo.chat.route.biz.dto.LoginDTO;
import io.ganguo.chat.route.biz.dto.UserDTO;
import io.ganguo.chat.route.biz.entity.Login;
import io.ganguo.chat.route.session.ClientSession;
import io.ganguo.chat.route.session.ClientSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Tony
 * @createAt Feb 17, 2015
 */
@Component
public class RouteHandler extends IMHandler<IMRequest> {
    private Logger logger = LoggerFactory.getLogger(RouteHandler.class);


    @Override
    public short getId() {
        return Handlers.USER;
    }

    @Override
    public void dispatch(IMConnection connection, IMRequest request) {
        Header header = request.getHeader();
        switch (header.getCommandId()) {
            case Commands.LOGIN_REQUEST:
                login(connection, request);
                break;
            case Commands.LOGIN_CHANNEL_REQUEST:
                loginChannel(connection, request);
                break;
            default:
                connection.close();
                break;
        }
    }

    private void loginChannel(IMConnection connection, IMRequest request) {
        LoginDTO loginDTO = request.readEntity(LoginDTO.class);
        Login login = loginDTO.getLogin();

        //TODO 这里改成本地验证 验证正确的服务器连接进来
      //  boolean isSuccess = userService.authenticate(login.getUin(), login.getAuthToken());
        IMResponse resp = new IMResponse();
        Header header = request.getHeader();
        if (true) {

            header.setHandlerId(getId());
            header.setCommandId(Commands.LOGIN_CHANNEL_SUCCESS);
            resp.setHeader(header);
           // resp.writeEntity(new UserDTO(user));
            connection.sendResponse(resp);

           /* // 是否已经登录进来了，踢下线
            ClientSession old = ClientSessionManager.getInstance().get(login.getUin());
            if (old != null && old.getConnection() != connection) {
                kick(old.getConnection(), request);
            }
            // 绑定用户UIN到connection中
            ClientSession session = new ClientSession(login, connection);*/
           // ClientSessionManager.getInstance().add(session);
        } else {
            header.setHandlerId(getId());
            header.setCommandId(Commands.LOGIN_CHANNEL_FAIL);
            resp.setHeader(header);
            connection.sendResponse(resp);
            connection.close();
        }
    }

    private void login(IMConnection connection, IMRequest request) {
        UserDTO userDTO = request.readEntity(UserDTO.class);
        //这里 account 是server的ip 和 端口
        String server = userDTO.getUser().getcServerIp();
        String password = userDTO.getUser().getPassword();
        Boolean isServer = validServer(server, password);

        IMResponse resp = new IMResponse();
        Header header = request.getHeader();
        if (isServer) {
            /*header.setHandlerId(getId());
            header.setCommandId(Commands.LOGIN_SUCCESS);
            resp.setHeader(header);
            connection.sendResponse(resp);*/

            // 是否已经登录进来了，踢下线
            ClientSession old = ClientSessionManager.getInstance().get(server);
            if (old != null && old.getConnection() != connection) {
                kick(old.getConnection(), request);
            }
            ClientSessionManager.getInstance().add(new ClientSession(server, connection));
        } else {
            header.setHandlerId(getId());
            header.setCommandId(Commands.LOGIN_FAIL);
            resp.setHeader(header);
            connection.sendResponse(resp);
            connection.close();
        }

    }

    /**
     * 被踢下线
     *
     * @param connection
     */
    private void kick(IMConnection connection, IMRequest request) {
        // send 离线信息，并kill
        IMResponse resp = new IMResponse();
        Header header = request.getHeader();
        header.setHandlerId(getId());
        header.setCommandId(Commands.LOGIN_CHANNEL_KICKED);
        resp.setHeader(header);
        connection.sendResponse(resp);
        connection.close();
    }

    /**
     * 验证服务器是否正确
     *
     */
    private boolean validServer(String serverUrl,String password){
        return true;
    }
}
