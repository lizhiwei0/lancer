package org.lizhiwei.lancer.api;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public interface ChannelMessageCodecFactory {

     MessageToByteEncoder getEncoder();

    ByteToMessageDecoder getDecoder();

}
