package org.lizhiwei.lancer.codec;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import org.apache.log4j.Logger;
import org.lizhiwei.lancer.api.Decoder;
import org.lizhiwei.lancer.api.Header;
import org.lizhiwei.lancer.api.Message;
import org.lizhiwei.lancer.config.CharsetHelper;
import org.lizhiwei.lancer.config.TypeHelper;
import org.lizhiwei.lancer.internal.LancerMessage;
import org.lizhiwei.lancer.internal.LancerMsgHeader;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class GsonLancerMessageDecoder implements Decoder<Message>{
    private static Logger logger = Logger.getLogger(GsonLancerMessageDecoder.class.getName());
    private final Gson gson = new Gson();

    public int getMessageLength(ByteBuf buf) {
        if (buf.readableBytes() < LancerMessage.HEADER_LENGTH) {
            return LancerMessage.HEADER_LENGTH;
        }

        byte[] len = new byte[12];

        buf.markReaderIndex();
        buf.readBytes(len);
        int readIndex = buf.readerIndex();
        buf.resetReaderIndex();
        int readIndex2 = buf.readerIndex();


        return len[0] << 24 |
                (len[1] & 0xff) << 16 |
                (len[2] & 0xff) <<  8 |
                len[3]  & 0xff;
    }

    public Message decode(Message msg, ByteBuf buf) {

        if (msg == null) {
            msg = new LancerMessage();
        }

        int i = buf.readableBytes();
        if (i < LancerMsgHeader.HEAD_LENGTH) {
            throw new RuntimeException("bad message");
        }

        LancerMsgHeader header = new LancerMsgHeader();
        header.setId(buf.readLong());
        header.setLength(buf.readInt());
        header.setCharset(CharsetHelper.getNameById(buf.readInt()));
        header.setType(TypeHelper.getNameById(buf.readInt()));
        Class clazz = null;
        try {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(header.getType());
        } catch (ClassNotFoundException e) {
            logger.error("decoder error",e);
        }

        int rest = buf.readableBytes();
        byte[] payload = new byte[rest];
        buf.readBytes(payload);

        String rawJson = new String(payload, Charset.forName(header.getCharset()));

        if (clazz != null) {
            try {
                msg.setBody(gson.fromJson(rawJson, clazz));
            }catch(Exception e) {

            }

        } else {
            msg.setBody(rawJson);
        }
        msg.setHeader(header);
        return msg;
    }
}
