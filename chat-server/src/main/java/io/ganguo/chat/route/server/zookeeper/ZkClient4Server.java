package io.ganguo.chat.route.server.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

/**
 * Created by James on 2015/11/16 0016.
 */
public class ZkClient4Server {

    /*private ZkClient zkClient;

    public ZkClient connect(){

        zkClient = new ZkClient("127.0.0.1:2181",5000);

        return zkClient;
    }



    public void CreateZkNode(final String pathAndPort){

        connect();

        if(zkClient.exists("/ChatRoute")){
            if(!zkClient.exists(pathAndPort)){
                zkClient.create(pathAndPort, new Long(System.currentTimeMillis()),CreateMode.EPHEMERAL);

            }
        }

    }

    public void zkNodeWatcher(){


        zkClient.subscribeDataChanges("", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

                //此服务在zk上被取消 查看原因
            }
        });
    }*/
    private static final String SERVER_PATH = "chatservers";
    private static CuratorFramework createCurator(String connectionURL){

        return CuratorFrameworkFactory.builder().connectString(connectionURL)
                .connectionTimeoutMs(60000).retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000000))
                .sessionTimeoutMs(60000).namespace(SERVER_PATH).build();

    }

    public static  void provideServer(String zkUrl ,String connectionURL){

        try {
            CuratorFramework framework = createCurator(zkUrl);
            framework.start();
           // framework.create().withMode(CreateMode.EPHEMERAL).forPath("/CHAT_SERVERS/"+connectionURL);
        //.withMode(CreateMode.EPHEMERAL)
         //   framework.create().withMode(CreateMode.PERSISTENT).forPath("/CHAT_SERVERS" );
            framework.create().withMode(CreateMode.EPHEMERAL).forPath("/CHAT_SERVERS/"+ connectionURL, connectionURL.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
