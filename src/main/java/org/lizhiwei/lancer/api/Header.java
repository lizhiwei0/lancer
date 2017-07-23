package org.lizhiwei.lancer.api;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public interface Header {
    long getId();
    String getCharset();
    int getLength();

    String getType();
}
