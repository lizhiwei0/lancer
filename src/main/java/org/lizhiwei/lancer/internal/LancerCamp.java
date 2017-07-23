package org.lizhiwei.lancer.internal;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.lizhiwei.lancer.api.Lancer;
import org.lizhiwei.lancer.api.LancerFactory;
import org.lizhiwei.lancer.config.ClassPathResourceLoader;
import org.lizhiwei.lancer.config.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class LancerCamp implements LancerFactory<String> {

    @Override
    public Lancer buildLancer(String name) {
        Yaml yaml = new Yaml();
        InputStream stream = resourceLoader.loadResource("/"+name + ".bootstrap.yml");
        Configuration cfg = yaml.loadAs(new InputStreamReader(stream),Configuration.class);
        return new LancerImpl(cfg);
    }

    private Logger logger= Logger.getLogger(LancerCamp.class.getCanonicalName());


    private ClassPathResourceLoader resourceLoader;

    public void setResourceLoader(ClassPathResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public LancerCamp() {
        //
    }


}
