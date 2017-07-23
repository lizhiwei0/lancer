package org.lizhiwei.lancer.codec;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.lizhiwei.lancer.api.Encoder;
import org.lizhiwei.lancer.api.Header;
import org.lizhiwei.lancer.api.Lancer;
import org.lizhiwei.lancer.config.CharsetHelper;
import org.lizhiwei.lancer.config.TypeHelper;
import org.lizhiwei.lancer.internal.LancerMessage;
import org.lizhiwei.lancer.internal.LancerMsgHeader;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class GsonEncoder extends MessageToByteEncoder {

    private Encoder encoder ;

    public Encoder getEncoder() {
        return encoder;
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (out == null) {
         out =  ctx.alloc().buffer();
        }

        if (! (msg instanceof LancerMessage)) {
            return;
        }

        ByteBuf encoded = encoder.encode((LancerMessage)msg);
        out.writeBytes(encoded);
    }



}
