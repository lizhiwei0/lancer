package org.lizhiwei.lancer.codec;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.lizhiwei.lancer.api.ChannelMessageCodecFactory;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class LancerMessageCodecFactory implements ChannelMessageCodecFactory {

    private GsonLancerCodecFactory codecFactory = new GsonLancerCodecFactory();

    @Override
    public MessageToByteEncoder getEncoder() {
        GsonEncoder encoder = new GsonEncoder();
        encoder.setEncoder(codecFactory.getEncoder());
        return encoder;
    }

    @Override
    public ByteToMessageDecoder getDecoder() {
        GsonDecoder decoder = new GsonDecoder();
        decoder.setDecoder(codecFactory.getDecoder());
        return decoder;
    }
}
