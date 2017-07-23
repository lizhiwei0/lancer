package org.lizhiwei.lancer.codec;

import org.lizhiwei.lancer.api.CodecFactory;
import org.lizhiwei.lancer.api.Decoder;
import org.lizhiwei.lancer.api.Encoder;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class GsonLancerCodecFactory implements CodecFactory {

    private Encoder encoder = new GsonLancerMessageEncoder();
    private Decoder decoder = new GsonLancerMessageDecoder();

    @Override
    public Encoder getEncoder() {
        return encoder;
    }

    @Override
    public Decoder getDecoder() {
        return decoder;
    }
}
