package org.lizhiwei.lancer.api;

import java.util.concurrent.Future;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public interface Lancer extends LifeCycle{

    public  Future connect(RemoteIdentifier remoteIdentifier);

    public  void disconnect(RemoteIdentifier remoteIdentifier);

    public boolean isConnected(RemoteIdentifier remoteIdentifier);

    public boolean send(Message msg, RemoteIdentifier remoteIdentifier);

    public String getName();

    LancerConnection getLancerConnection(RemoteIdentifier remoteIdentifier);

    public boolean addLancerConnection(LancerConnection lancerConnection);

    public boolean removeLancerConnection(RemoteIdentifier remoteIdentifier);
}
