package org.lizhiwei.lancer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.apache.log4j.Logger;
import org.lizhiwei.lancer.internal.LancerMessage;
import org.lizhiwei.lancer.internal.LancerMsgHeader;
import org.lizhiwei.lancer.model.Command;
import org.lizhiwei.lancer.model.WaterMelon;

import java.net.SocketAddress;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class WaterMelonSender extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(getClass().getName());

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();

        logger.info("----------------"+getClass().getName()+" regitered----------------");
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
        if (msg instanceof LancerMessage) {
           WaterMelon melon = WaterMelon.randomWaterMelon();
            LancerMessage lancerMessage  = new LancerMessage();
            LancerMsgHeader header = new LancerMsgHeader();
            header.setCharset("UTF-8");
            header.setId(1);
            header.setType(WaterMelon.class.getName());
            lancerMessage.setHeader(header);
            lancerMessage.setBody(melon);
            logger.info("received :"+msg+" send out :"+ melon);
            ctx.writeAndFlush(lancerMessage);
        }

    }



}
