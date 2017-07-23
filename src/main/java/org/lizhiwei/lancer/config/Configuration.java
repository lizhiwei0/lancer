package org.lizhiwei.lancer.config;

import java.util.*;

/**
 * Created by lizhiwe on 7/15/2017.
 */
public class Configuration {
    public static final String PARENT_THREAD_NUM = "ParentThreadNumber";
    public static final String CHILD_THREAD_NUM = "ChildThreadNumber";
    public static final String PROTOCOL0 = "Protocol";


    private int parentThreadNumber = 4;
    private int childThreadNumber = 8;
    private Protocol protocol;
    private Map channelOptions = new HashMap();
    private List<String> handlers = new ArrayList<String>();
    private Mode mode;
    private String host;
    private int port;

    private String name;

    private String codecFactory;


    private Map<String,Integer> charsets = new HashMap<String,Integer>();


    public Map<String, Integer> getCharsets() {
        return charsets;
    }

    public void setCharsets(Map<String, Integer> charsets) {
        this.charsets = charsets;
        if (charsets != null) {
            CharsetHelper.addCharsets(charsets);
        }
    }



    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Map getChannelOptions() {
        return channelOptions;
    }

    public void setChannelOptions(Map channelOptions) {
        this.channelOptions = channelOptions;
    }

    public List<String> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<String> handlers) {
        this.handlers = handlers;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public int getParentThreadNumber() {
        return parentThreadNumber;
    }

    public void setParentThreadNumber(int parentThreadNumber) {
        this.parentThreadNumber = parentThreadNumber;
    }

    public int getChildThreadNumber() {
        return childThreadNumber;
    }

    public void setChildThreadNumber(int childThreadNumber) {
        this.childThreadNumber = childThreadNumber;
    }

    public static Map<String,Object> normalizeChannelOptions(Map options) {

        Map<String,Object> res = new HashMap<String,Object>();
        if (options == null) {
            return res;
        }
        for (Object item : options.entrySet()) {

            Map.Entry entry = (Map.Entry) item;

            if (booleanOpts.contains(entry.getKey())){
                res.put(entry.getKey().toString(),Boolean.valueOf(entry.getValue().toString()));
            } else if(intOpts.contains(entry.getKey())) {
                res.put(entry.getKey().toString(),Integer.valueOf(entry.getValue().toString()));
            }
        }

        return res;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodecFactory() {
        return codecFactory;
    }

    public void setCodecFactory(String codecFactory) {
        this.codecFactory = codecFactory;
    }


    public enum Protocol {
        TCP,
        UDP,
        SCTP
    }

    public enum Mode {
        CLIENT,
        SERVER,
        PEER
    }

//    public static final ChannelOption<ByteBufAllocator> ALLOCATOR = valueOf("ALLOCATOR");
//    public static final ChannelOption<RecvByteBufAllocator> RCVBUF_ALLOCATOR = valueOf("RCVBUF_ALLOCATOR");
//    public static final ChannelOption<MessageSizeEstimator> MESSAGE_SIZE_ESTIMATOR = valueOf("MESSAGE_SIZE_ESTIMATOR");
//    public static final ChannelOption<WriteBufferWaterMark> WRITE_BUFFER_WATER_MARK =
//            valueOf("WRITE_BUFFER_WATER_MARK");



    public static List<String> booleanOpts = Arrays.asList("ALLOW_HALF_CLOSURE","AUTO_READ","AUTO_CLOSE","SO_BROADCAST","SO_KEEPALIVE","SO_REUSEADDR","IP_MULTICAST_LOOP_DISABLED","TCP_NODELAY","DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION"
            ,"SINGLE_EVENTEXECUTOR_PER_GROUP");

    public static List<String> intOpts = Arrays.asList("CONNECT_TIMEOUT_MILLIS","MAX_MESSAGES_PER_READ","WRITE_SPIN_COUNT","WRITE_BUFFER_HIGH_WATER_MARK","SO_SNDBUF","SO_RCVBUF","SO_LINGER",
            "SO_BACKLOG","SO_TIMEOUT","IP_TOS","IP_MULTICAST_ADDR","IP_MULTICAST_IF","IP_MULTICAST_TTL","WRITE_BUFFER_LOW_WATER_MARK");
}
