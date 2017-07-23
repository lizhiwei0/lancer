package org.lizhiwei.lancer.internal;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpChannel;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import net.openhft.affinity.AffinityStrategies;
import net.openhft.affinity.AffinityThreadFactory;
import org.apache.log4j.Level;
import org.lizhiwei.lancer.api.*;
import org.lizhiwei.lancer.config.Configuration;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import org.apache.log4j.Logger;
import org.lizhiwei.lancer.internal.handler.ConnectionMaintainHandler;
import org.lizhiwei.lancer.util.InstanceBuilder;

import java.net.InetSocketAddress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class LancerImpl implements Lancer, LifeCycle {

    private Logger logger = Logger.getLogger(Lancer.class.getName());

    private ServerBootstrap serverBootstrap = null;

    private Bootstrap bootstrap;

    private Configuration cfg;

    protected final Map<Configuration.Protocol, Class> serverChannelClasses = new HashMap<>();

    protected final Map<Configuration.Protocol, Class> channelClasses = new HashMap<>();

    protected Map<RemoteIdentifier, LancerConnection> connectionMap = new ConcurrentHashMap<>();

    protected ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(16);

    private ChannelMessageCodecFactory codecFactory;

    private ChannelFuture hook;

    private final Lock lock = new ReentrantLock();

    private ConnectionMaintainHandler connectionMaintainHandler ;

    public LancerImpl() {
        serverChannelClasses.put(Configuration.Protocol.TCP, NioServerSocketChannel.class);
        serverChannelClasses.put(Configuration.Protocol.UDP, NioDatagramChannel.class);
        serverChannelClasses.put(Configuration.Protocol.SCTP, NioSctpServerChannel.class);
        //
        channelClasses.put(Configuration.Protocol.TCP, NioSocketChannel.class);
        channelClasses.put(Configuration.Protocol.UDP, NioDatagramChannel.class);
        channelClasses.put(Configuration.Protocol.SCTP, NioSctpChannel.class);
        //
        connectionMaintainHandler = new ConnectionMaintainHandler(this,60,60,60);
    }

    public LancerImpl(Configuration cfg) {
        this();
        this.cfg = cfg;
    }

    public void setConfiguration(Configuration cfg) {
        this.cfg = cfg;
    }


    public Future connect(final RemoteIdentifier remoteIdentifier) {
        if (bootstrap == null) {
            throw new RuntimeException("Server NOT support connect");
        }
        try {
            final ChannelFuture future = bootstrap.connect((java.net.SocketAddress) remoteIdentifier.getPeerAddress());
            future.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Void>>() {
                @Override
                public void operationComplete(io.netty.util.concurrent.Future<? super Void> f) throws Exception {
                    if (f.isSuccess()) {
                        // lancerConnection.setChannel(future.channel());
                        return;
                    }
                    executor.schedule(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                connect(remoteIdentifier);
                            } catch (Exception e) {
                                logger.error("build connection exception", e);
                            }
                        }
                    }, 5, TimeUnit.SECONDS);

                }
            });
            return future;
        } catch (Exception e) {
            logger.error("build connection exception", e);
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        connect(remoteIdentifier);
                    } catch (Exception e) {
                        logger.error("build connection exception", e);
                    }

                }
            }, 5, TimeUnit.SECONDS);
        }

        return null;

    }

    @Override
    public void disconnect(RemoteIdentifier remoteIdentifier) {
        lock.lock();
        try {
            LancerConnection connection = connectionMap.get(remoteIdentifier);
            if (connection == null) {
                return;
            }
            if (connection.getChannel().isActive()) {
                connection.getChannel().disconnect();
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean isConnected(RemoteIdentifier remoteIdentifier) {
        LancerConnection connection = connectionMap.get(remoteIdentifier);
        if (connection == null) {
            return false;
        }
        return connection.isConnected();
    }

    @Override
    public boolean send(Message msg, RemoteIdentifier remoteIdentifier) {
        LancerConnection connection = connectionMap.get(remoteIdentifier);
        if (logger.isDebugEnabled()) {
            logger.debug("got connection :"+connection+" with remoteId:"+remoteIdentifier);
        }
        if (connection == null) {
            return false;
        }
        return connection.send(msg);
    }

    @Override
    public String getName() {
        return this.cfg.getName();
    }

    @Override
    public LancerConnection getLancerConnection(RemoteIdentifier remoteIdentifier) {
        return connectionMap.get(remoteIdentifier);
    }

    @Override
    public boolean addLancerConnection(LancerConnection lancerConnection) {
        logger.info("lancer connection:"+lancerConnection.getChannel().remoteAddress()+" registered");
        this.connectionMap.put(lancerConnection.getIdentifier(), lancerConnection);
        return true;
    }

    @Override
    public boolean removeLancerConnection(RemoteIdentifier remoteIdentifier) {
        this.connectionMap.remove(remoteIdentifier);
        logger.info("lancer connection:"+remoteIdentifier.getPeerAddress()+" unregistered");
        return true;
    }


    @Override
    public void create() {



       // NioEventLoopGroup workerGroup = new NioEventLoopGroup(cfg.getChildThreadNumber());

        AffinityStrategies strategies = null;
        if (cfg.getThreadAffinityStrategy() != null) {
            try {
                strategies =  AffinityStrategies.valueOf(cfg.getThreadAffinityStrategy());
            }catch (Exception e){
               logger.error("illegal threadAffinityStrategy, ignore threadAffinityStrategy");
            }
        }

        EventLoopGroup workerGroup = null;
        if (strategies != null) {
            ThreadFactory threadFactory = new AffinityThreadFactory("atf_wrk", strategies);
            workerGroup = new NioEventLoopGroup(cfg.getChildThreadNumber(), threadFactory);
        } else {
            workerGroup = new NioEventLoopGroup(cfg.getChildThreadNumber());
        }

        codecFactory = InstanceBuilder.buildInstance(cfg.getCodecFactory());

        if (!cfg.getMode().equals(Configuration.Mode.CLIENT)) {
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(new NioEventLoopGroup(cfg.getParentThreadNumber()), workerGroup)
                    .channel(serverChannelClasses.get(cfg.getProtocol()));
            Map<String, Object> opts = Configuration.normalizeChannelOptions(cfg.getChannelOptions());

            for (Object item : opts.entrySet()) {
                Map.Entry entry = (Map.Entry) item;
                ChannelOption option = ChannelOption.valueOf(entry.getKey().toString());
                serverBootstrap.childOption(option, entry.getValue());
            }
            buildServerHandlers();

        }

        if (!cfg.getMode().equals(Configuration.Mode.SERVER)) {
            bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(channelClasses.get(cfg.getProtocol()));
            Map<String, Object> opts = Configuration.normalizeChannelOptions(cfg.getChannelOptions());

            for (Object item : opts.entrySet()) {
                Map.Entry entry = (Map.Entry) item;
                ChannelOption option = ChannelOption.valueOf(entry.getKey().toString());
                bootstrap.option(option, entry.getValue());
            }

            buildClientHandlers();

        }
    }

    protected void buildCodecHandlers(ChannelPipeline pipeline) {
        if (codecFactory == null) {
            return;
        }
        pipeline.addFirst().addFirst("byteToMsgEncoder", codecFactory.getEncoder());
        pipeline.addFirst().addFirst("decoderToMsgEncoder", codecFactory.getDecoder());
    }

    protected void buildConnectionMaintainHandler(ChannelPipeline pipeline) {
        pipeline.addLast(connectionMaintainHandler);
    }


    protected void buildServerHandlers() {
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            public void initChannel(SocketChannel ch) throws Exception {
                buildCodecHandlers(ch.pipeline());
                buildConnectionMaintainHandler(ch.pipeline());
                for (String channelHandlers : cfg.getHandlers()) {
                    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(channelHandlers);
                    ChannelHandler handler = (ChannelHandler) clazz.newInstance();
                    logger.debug("register " + channelHandlers + " to serverbootstrap");
                    ch.pipeline().addLast(handler);
                }
            }
        });
    }

    protected void buildClientHandlers() {
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                buildCodecHandlers(ch.pipeline());
                buildConnectionMaintainHandler(ch.pipeline());
                for (String channelHandlers : cfg.getHandlers()) {
                    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(channelHandlers);
                    ChannelHandler handler = (ChannelHandler) clazz.newInstance();
                    logger.debug("register " + channelHandlers + " to bootstrap");
                    ch.pipeline().addLast(handler);
                }
            }
        });
    }

    @Override
    public void start() {
        try {
            if (serverBootstrap != null) {
                hook = serverBootstrap.bind(cfg.getHost(), cfg.getPort()).sync();
            }
            logger.log(Level.INFO, "Lancer " + this.cfg.getName() + " started");
        } catch (InterruptedException e) {
            logger.error("start up exception", e);
        }
    }

    @Override
    public void stop() {
        if (hook == null) {
            return;
        }

        try {
            hook.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (serverBootstrap != null) {
            serverBootstrap.config().group().shutdownGracefully();
            serverBootstrap.config().childGroup().shutdownGracefully();
        }
        if (bootstrap != null
                && !bootstrap.config().group().isShuttingDown()) {
            bootstrap.config().group().shutdownGracefully();
        }
    }

    public LancerConnection getConnection(RemoteIdentifier remoteIdentifier) {
        return connectionMap.get(remoteIdentifier);
    }

    @Override
    public void destory() {
        //TODO
    }
}
