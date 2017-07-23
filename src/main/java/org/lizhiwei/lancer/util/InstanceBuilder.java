package org.lizhiwei.lancer.util;

import java.lang.reflect.Constructor;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class InstanceBuilder {

    public static <T> T buildInstance(String className) {
        if (className == null) {
            return null;
        }
        try {
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            Constructor<T> c = clazz.getConstructor();
            return c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
