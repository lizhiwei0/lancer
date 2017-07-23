package org.lizhiwei.lancer.api;

import io.netty.channel.Channel;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public interface LancerConnection {

    Channel getChannel();

    boolean send(Message msg);

    boolean isConnected();

    boolean isAvaliable();

    RemoteIdentifier getIdentifier();
}
