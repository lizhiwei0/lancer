package org.lizhiwei.lancer.api;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public interface LancerFactory<T> {

    public Lancer buildLancer(T context);
}
