package org.lizhiwei.lancer.internal;

import org.lizhiwei.lancer.api.Header;

import java.nio.charset.Charset;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class LancerMsgHeader implements Header {

    public static final int HEAD_LENGTH = 8+4+4+4;

    public String charset = "UTF-8";
    private long id;
    private int length;
    private String type = "json";

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setType(String type) {
        this.type = type;
    }



    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getCharset() {
        return charset;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public String getType() {
        return type;
    }
}
