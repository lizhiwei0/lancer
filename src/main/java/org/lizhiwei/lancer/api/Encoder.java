package org.lizhiwei.lancer.api;

import io.netty.buffer.ByteBuf;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public interface Encoder<T> {

    ByteBuf encode(Message msg);

}
