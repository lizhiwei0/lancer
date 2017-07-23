package org.lizhiwei.lancer.internal;

import io.netty.channel.Channel;
import org.lizhiwei.lancer.api.LancerConnection;
import org.lizhiwei.lancer.api.Message;
import org.lizhiwei.lancer.api.RemoteIdentifier;

import java.net.InetSocketAddress;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class LancerConnectionImpl implements LancerConnection {
    private RemoteIdentifier<InetSocketAddress> remoteIdentifier;
    public LancerConnectionImpl(RemoteIdentifier<InetSocketAddress> remoteIdentifier) {
        this.remoteIdentifier = remoteIdentifier;
    }

    private  Channel channel ;


    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public boolean send(Message msg) {
        if (isAvaliable() && channel.isWritable()) {
            channel.writeAndFlush(msg);
            return true;
        }

        return false;
    }

    @Override
    public boolean isConnected() {
        return channel != null && channel.isOpen();
    }

    @Override
    public boolean isAvaliable() {
        return channel != null && channel.isActive();
    }

    @Override
    public RemoteIdentifier getIdentifier() {
        return this.remoteIdentifier;
    }

    public void setChannel(io.netty.channel.Channel channel) {
        this.channel = channel;
    }
}
