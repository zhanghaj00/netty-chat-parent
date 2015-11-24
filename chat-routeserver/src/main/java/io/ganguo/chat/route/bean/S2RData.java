package io.ganguo.chat.route.bean;

import io.ganguo.chat.core.transport.DataBuffer;
import io.ganguo.chat.core.transport.IMSerializer;

/**
 * Created by James on 2015/11/19 0019.
 */
public class S2RData implements IMSerializer{






    @Override
    public DataBuffer encode(short version) {
        return null;
    }

    @Override
    public void decode(DataBuffer buffer, short version) {

    }
}
