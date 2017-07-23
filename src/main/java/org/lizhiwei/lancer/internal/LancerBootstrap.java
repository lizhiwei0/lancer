package org.lizhiwei.lancer.internal;

import org.lizhiwei.lancer.api.LifeCycle;
import org.lizhiwei.lancer.config.CharsetHelper;
import org.lizhiwei.lancer.config.ClassPathResourceLoader;
import org.lizhiwei.lancer.config.TypeHelper;

/**
 * Created by lizhiwe on 7/17/2017.
 */
public class LancerBootstrap implements LifeCycle{

    private LancerCamp lancerCamp;

    @Override
    public void create() {
        ClassPathResourceLoader resourceLoader = new ClassPathResourceLoader(LancerBootstrap.class);
        TypeHelper.getInstance().loadTypes(resourceLoader);
        CharsetHelper.getInstance().load(resourceLoader);
        lancerCamp = new LancerCamp();

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destory() {

    }
}
