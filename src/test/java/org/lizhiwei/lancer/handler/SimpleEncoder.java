package org.lizhiwei.lancer.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class SimpleEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (out == null) {
            out = ctx.alloc().buffer();
        }

        out.writeBytes(msg.toString().getBytes());

    }
}
