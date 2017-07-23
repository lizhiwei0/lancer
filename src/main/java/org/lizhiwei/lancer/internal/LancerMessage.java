package org.lizhiwei.lancer.internal;

import org.lizhiwei.lancer.api.Header;
import org.lizhiwei.lancer.api.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class LancerMessage implements Message {
    public static final int HEADER_LENGTH = 20;
    private Header header;
    private Object body;
    private Map ctx = new HashMap();

    @Override
    public String toString() {
        return "LancerMessage{" +
                "header=" + header +
                ", body=" + body +
                ", ctx=" + ctx +
                '}';
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public Map getContext() {
        return ctx;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }
}
