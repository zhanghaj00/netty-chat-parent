package io.ganguo.chat.route.server.cpooled;

import io.netty.channel.Channel;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by James on 2015/11/18 0018.
 */
public class S2RChannelFactory {

    private GenericObjectPool<Channel> pool = null;

    private S2RChannelFactory(){

        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMaxTotal(20);
        conf.setMaxIdle(10);
        pool = new GenericObjectPool<Channel>(new ChannelPooledFactory(), conf);
    }

    public GenericObjectPool<Channel> getPool(){
        return  pool;
    }

    private static class S2RChannelFactoryHolder{
        public S2RChannelFactory  s2RHolder = new S2RChannelFactory();
    }


    public Channel getChannel(){
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnChannel(Channel channel){
        pool.returnObject(channel);
    }

    public static final S2RChannelFactory getS2RChannelInstance(){
        return new S2RChannelFactoryHolder().s2RHolder;
    }
}
