package org.lizhiwei.lancer.api;

import java.util.Map;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public interface Message <T> {

    public Header getHeader();

    public void setHeader(Header  header);

    public Map getContext();

    public T getBody();

    public void setBody(T body);
}
