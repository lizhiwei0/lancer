package org.lizhiwei.lancer.codec;

import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lizhiwei.lancer.ModelHelper;
import org.lizhiwei.lancer.api.Message;
import org.lizhiwei.lancer.config.TypeHelper;
import org.lizhiwei.lancer.internal.LancerMessage;
import org.lizhiwei.lancer.model.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class GsonCodecTest {

    @Before
    public void prepare() {
        Map<String, Integer> chs = new HashMap<>();
        chs.put(Command.class.getName(), 1);
        TypeHelper.addTypes(chs);
    }

    @Test
    public void testEncoder() {

        Message message = ModelHelper.buildMessage();

        GsonLancerMessageEncoder encoder = new GsonLancerMessageEncoder();

        ByteBuf encoded = encoder.encode(message);

        GsonLancerMessageDecoder decoder = new GsonLancerMessageDecoder();

        LancerMessage message2 = new LancerMessage();

        Message msg2 = decoder.decode(message2, encoded);

        Assert.assertNotNull(msg2.getBody());

        Command cmd2 = (Command) msg2.getBody();

        Assert.assertEquals(cmd2, message.getBody());

    }
}
