package com.watermelon.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                //1.指定线程模型
                .group(workerGroup)
                //2.指定IO类型为NIO
                .channel(NioSocketChannel.class)
                //3.IO处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });

        //attr() 方法可以给客户端 Channel，也就是NioSocketChannel绑定自定义属性，然后我们可以通过channel.attr()取出这个属性
        bootstrap.attr(AttributeKey.newInstance("clientName"), "clientValue");

        bootstrap
                //表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //表示是否开启 TCP 底层心跳机制，true 为开启
                .option(ChannelOption.SO_KEEPALIVE, true)
                //表示是否开始 Nagle 算法，true 表示关闭，false 表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，
                // 就设置为 true 关闭，如果需要减少发送次数减少网络交互，就设置为 false 开启
                .option(ChannelOption.TCP_NODELAY, true);

        //建立连接
        connect(bootstrap, "127.0.0.1", 1000, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retry == 0) {
                System.err.println("多次重连，最终失败");
            } else {
                //第几次重试
                int order = MAX_RETRY - retry + 1;
                //计算延迟时间
                long delay = order << 1;
                System.err.println("第" + order + "次重连失败");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay
                        , TimeUnit.SECONDS);
            }
        });
    }

}
