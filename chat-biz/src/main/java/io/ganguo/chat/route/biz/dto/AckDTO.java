package io.ganguo.chat.route.biz.dto;

import io.ganguo.chat.core.transport.DataBuffer;
import io.ganguo.chat.core.transport.IMSerializer;

/**
 * Created by Tony on 2/20/15.
 */
public class AckDTO implements IMSerializer {

    private long to;
    private String ackId;

    private String toServer;

    public AckDTO() {
    }

    public AckDTO(long to, String ackId,String toServer) {
        this.to = to;
        this.ackId = ackId;
        this.toServer = toServer;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public String getAckId() {
        return ackId;
    }

    public void setAckId(String ackId) {
        this.ackId = ackId;
    }


    public String getToServer() {
        return toServer;
    }

    public void setToServer(String toServer) {
        this.toServer = toServer;
    }

    @Override
    public DataBuffer encode(short version) {
        DataBuffer buffer = new DataBuffer();
        buffer.writeLong(to);
        buffer.writeString(ackId);
        buffer.writeString(toServer);
        return buffer;
    }

    @Override
    public void decode(DataBuffer buffer, short version) {
        to = buffer.readLong();
        ackId = buffer.readString();
        toServer = buffer.readString();
    }

    @Override
    public String toString() {
        return "AckDTO{" +
                "to=" + to +
                ", ackId='" + ackId + '\'' +
                '}';
    }
}
