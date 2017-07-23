package org.lizhiwei.lancer;

import junit.framework.Assert;
import org.junit.Test;
import org.lizhiwei.lancer.api.Lancer;
import org.lizhiwei.lancer.config.ClassPathResourceLoader;
import org.lizhiwei.lancer.config.TypeHelper;
import org.lizhiwei.lancer.internal.LancerCamp;
import org.lizhiwei.lancer.model.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class LancerServerTest {

    @Test
    public  void testLancerServer() {

        TypeHelper.getInstance().loadTypes(new ClassPathResourceLoader(LancerServerTest.class));
        LancerCamp lancerCamp = new LancerCamp();

        ClassPathResourceLoader resourceLoader = new ClassPathResourceLoader(getClass());

        //getClass().getResource()
        lancerCamp.setResourceLoader(resourceLoader);
        Lancer lancer =  lancerCamp.buildLancer("lancer_message");
        Assert.assertNotNull(lancer);
        lancer.create();;
        lancer.start();
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
