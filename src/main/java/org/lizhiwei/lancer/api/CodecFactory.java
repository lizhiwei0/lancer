package org.lizhiwei.lancer.api;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public interface CodecFactory {

    Encoder getEncoder();

    Decoder getDecoder();

}
