package org.lizhiwei.lancer.codec;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.lizhiwei.lancer.api.Encoder;
import org.lizhiwei.lancer.api.Header;
import org.lizhiwei.lancer.api.Message;
import org.lizhiwei.lancer.config.CharsetHelper;
import org.lizhiwei.lancer.config.TypeHelper;
import org.lizhiwei.lancer.internal.LancerMessage;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class GsonLancerMessageEncoder implements Encoder<Message>{
    private final Gson gson = new Gson();

    public ByteBuf encode(Message msg) {
        LancerMessage lancerMessage = (LancerMessage)msg;
        String json = gson.toJson(lancerMessage.getBody());
        byte[] rawJson = json.getBytes();
        Header header = lancerMessage.getHeader();
        int length = LancerMessage.HEADER_LENGTH + rawJson.length;
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer(length);
        /////
        out.writeLong(header.getId());
        out.writeInt(length);
        out.writeInt(CharsetHelper.getIdByName(header.getCharset()));
        out.writeInt(TypeHelper.getIdByName(header.getType()));
        out.writeBytes(json.getBytes());
        return out;
    }
}
