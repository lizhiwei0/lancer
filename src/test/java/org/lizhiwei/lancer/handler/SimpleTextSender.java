package org.lizhiwei.lancer.handler;

import io.netty.channel.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.apache.log4j.Logger;

import java.net.SocketAddress;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class SimpleTextSender extends ChannelOutboundHandlerAdapter {

    private Logger logger = Logger.getLogger(getClass().getName());
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();

        logger.info("----------------SimpleTextSender registered----------------");
    }


    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                        SocketAddress localAddress, ChannelPromise promise) throws Exception {
        ctx.connect(remoteAddress, localAddress, promise);

        promise.addListener(new FutureListener() {

            @Override
            public void operationComplete(Future future) throws Exception {
                if(future.isSuccess()) {
                    ctx.writeAndFlush("Hello there!");
                    logger.debug("sending message to server"+ ctx.channel());
                }
            }
        });
    }
}
