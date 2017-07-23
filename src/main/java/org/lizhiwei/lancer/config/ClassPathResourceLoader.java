package org.lizhiwei.lancer.config;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class ClassPathResourceLoader {

    private Class clazz;

    private Logger logger = Logger.getLogger(getClass().getName());
    public ClassPathResourceLoader(Class clazz) {
        this.clazz = clazz;
    }

    public InputStream loadResource(String resource) {
        logger.info("loading "+resource);

        URL url = clazz.getResource(resource);

       // logger.info("----"+url.getFile());

        return clazz.getResourceAsStream(resource);
    }
}
