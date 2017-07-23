package org.lizhiwei.lancer.internal.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.resolver.InetSocketAddressResolver;
import org.lizhiwei.lancer.InetRemoteIdentifier;
import org.lizhiwei.lancer.api.Lancer;
import org.lizhiwei.lancer.internal.LancerConnectionImpl;

import java.net.InetSocketAddress;

/**
 * Created by lizhiwe on 7/17/2017.
 */

@ChannelHandler.Sharable
public class ConnectionMaintainHandler extends IdleStateHandler {
    private Lancer lancer;
    public ConnectionMaintainHandler(Lancer lancer, int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
        this.lancer = lancer;
    }

    public ConnectionMaintainHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // This method will be invoked only if this handler was added
        // before channelActive() event is fired.  If a user adds this handler
        // after the channelActive() event, initialize() will be called by beforeAdd().

        super.channelActive(ctx);
        InetRemoteIdentifier inetRemoteIdentifier = new InetRemoteIdentifier();
        inetRemoteIdentifier.setAddress((InetSocketAddress) ctx.channel().remoteAddress());
        LancerConnectionImpl lancerConnection = new LancerConnectionImpl(inetRemoteIdentifier);
        lancerConnection.setChannel(ctx.channel());
        lancer.addLancerConnection(lancerConnection);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetRemoteIdentifier inetRemoteIdentifier = new InetRemoteIdentifier();
        inetRemoteIdentifier.setAddress((InetSocketAddress) ctx.channel().remoteAddress());
        lancer.removeLancerConnection(inetRemoteIdentifier);
        super.channelInactive(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if (ctx.channel() != null && ctx.channel().remoteAddress() != null
                &&( !ctx.channel().isOpen() || !ctx.channel().isActive())) {
            InetRemoteIdentifier inetRemoteIdentifier = new InetRemoteIdentifier();
            inetRemoteIdentifier.setAddress((InetSocketAddress) ctx.channel().remoteAddress());
            lancer.removeLancerConnection(inetRemoteIdentifier);
        }

        ctx.fireExceptionCaught(cause);

    }



}
