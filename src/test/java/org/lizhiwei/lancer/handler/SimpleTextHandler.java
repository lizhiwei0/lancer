package org.lizhiwei.lancer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class SimpleTextHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(SimpleTextHandler.class.getName());

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();

        logger.info("SimpleTextHandler registered");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        logger.info("received:"+msg.toString());
    }
}
