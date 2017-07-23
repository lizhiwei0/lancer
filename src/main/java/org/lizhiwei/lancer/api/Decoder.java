package org.lizhiwei.lancer.api;

import io.netty.buffer.ByteBuf;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public interface Decoder<T> {

    int getMessageLength(ByteBuf buf);

    T decode(T msg,ByteBuf buf);
}
