package org.lizhiwei.lancer;

import org.lizhiwei.lancer.api.RemoteIdentifier;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class InetRemoteIdentifier implements RemoteIdentifier <InetSocketAddress> {
    private InetSocketAddress address;

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public InetSocketAddress getPeerAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InetRemoteIdentifier)) return false;

        InetRemoteIdentifier that = (InetRemoteIdentifier) o;

        return address.equals(that.address);

    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
