package org.lizhiwei.lancer.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.lizhiwei.lancer.api.Decoder;

import java.util.Base64;
import java.util.List;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class GsonDecoder extends ByteToMessageDecoder {

    private Decoder decoder;

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
       int i = in.readableBytes();

      int len =  decoder.getMessageLength(in);

        if (i < len) {
            return ;
        }


        Object decoded = decoder.decode(null, in);

        if (out != null) {
            out.add(decoded);
        }

    }
}
