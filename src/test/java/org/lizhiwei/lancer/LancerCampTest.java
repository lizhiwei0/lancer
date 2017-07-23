package org.lizhiwei.lancer;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import junit.framework.Assert;
import org.junit.Test;
import org.lizhiwei.lancer.api.Lancer;
import org.lizhiwei.lancer.api.RemoteIdentifier;
import org.lizhiwei.lancer.config.ClassPathResourceLoader;
import org.lizhiwei.lancer.config.Configuration;
import org.lizhiwei.lancer.internal.LancerCamp;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class LancerCampTest {

    private final Logger logger = Logger.getLogger(getClass().getName());

    //@Test
    public void testConfig() {
        Yaml yaml = new Yaml();
        ClassPathResourceLoader resourceLoader = new ClassPathResourceLoader(getClass());

        InputStream stream = resourceLoader.loadResource("/lancer.bootstrap.yml");

        Configuration cfg = yaml.loadAs(stream, Configuration.class);

        Assert.assertEquals(cfg.getMode(), Configuration.Mode.SERVER);
    }

    @Test
    public void testCreateLancers() {
        LancerCamp lancerCamp = new LancerCamp();

        ClassPathResourceLoader resourceLoader = new ClassPathResourceLoader(getClass());

        //getClass().getResource()
        lancerCamp.setResourceLoader(resourceLoader);
        Lancer lancer =  lancerCamp.buildLancer("lancer");

        Lancer lancerClient = lancerCamp.buildLancer("lancerclient");

        Assert.assertNotNull(lancer);
        Assert.assertNotNull(lancerClient);

        lancer.create();;
        lancerClient.create();

        lancer.start();

        lancerClient.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final  ChannelFuture channelFuture = (ChannelFuture) lancerClient.connect(new RemoteIdentifier() {
            @Override
            public Object getPeerAddress() {
                return new InetSocketAddress("localhost",8199);
            }
        });

        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                logger.info(future.isSuccess() ? "connected" : "connecting failed");

                //System.out.println(future.get());
                //logger.info(future.get());
                Thread.sleep(100);
                channelFuture.channel().write("Hello from client!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                channelFuture.channel().flush();

                Thread.sleep(100);
                channelFuture.channel().write("Hello from client");

                channelFuture.channel().flush();


            }
        });



        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
