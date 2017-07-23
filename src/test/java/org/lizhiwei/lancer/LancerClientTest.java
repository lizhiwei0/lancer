package org.lizhiwei.lancer;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import junit.framework.Assert;
import org.junit.Test;
import org.lizhiwei.lancer.api.Lancer;
import org.lizhiwei.lancer.api.RemoteIdentifier;
import org.lizhiwei.lancer.config.ClassPathResourceLoader;
import org.lizhiwei.lancer.config.TypeHelper;
import org.lizhiwei.lancer.internal.LancerCamp;
import org.lizhiwei.lancer.model.Command;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class LancerClientTest {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Test
    public void testLancerClient() {
        TypeHelper.getInstance().loadTypes(new ClassPathResourceLoader(LancerClientTest.class));
        LancerCamp lancerCamp = new LancerCamp();
        ClassPathResourceLoader resourceLoader = new ClassPathResourceLoader(getClass());
        lancerCamp.setResourceLoader(resourceLoader);
        Lancer lancerClient = lancerCamp.buildLancer("lancerclient_message");
        lancerClient.create();
        lancerClient.start();


        InetRemoteIdentifier serverIdentifier = new InetRemoteIdentifier();
        serverIdentifier.setAddress(new InetSocketAddress("localhost",8198));

        final ChannelFuture channelFuture = (ChannelFuture) lancerClient.connect(serverIdentifier);


        for (int i = 0; i< 1;i++) {

            try {
                Thread.sleep(1000);
                lancerClient.send(ModelHelper.buildMessage(),serverIdentifier);


                Thread.sleep(10000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
