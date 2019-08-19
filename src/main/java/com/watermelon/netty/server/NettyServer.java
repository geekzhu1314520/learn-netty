package com.watermelon.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {

        //bossGroup表示监听端口，accept 新连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //引导类 ServerBootstrap，这个类将引导我们进行服务端的启动工作
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(bossGroup, workerGroup) //给引导类配置两大线程组
                .channel(NioServerSocketChannel.class) //指定服务端的 IO 模型为NIO
                .childHandler(new ChannelInitializer<NioSocketChannel>() {  //定义后续每条连接的数据读写，业务处理逻辑
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //NioServerSocketChannel和NioSocketChannel是对NIO类型连接的抽象，
                        // 其概念可以和 BIO 编程模型中的ServerSocket以及Socket两个概念对应上
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });

        //childHandler()用于指定处理新连接数据的读写处理逻辑，handler()用于指定在服务端启动过程中的一些逻辑
        bootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) throws Exception {

            }
        });

        //attr()方法可以给服务端的 channel，也就是NioServerSocketChannel指定一些自定义属性，然后我们可以通过channel.attr()取出这个属性
        bootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");

        //上面的childAttr可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性。
        bootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");

        bootstrap
                .childOption(ChannelOption.SO_KEEPALIVE, true) //表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.TCP_NODELAY, true) //表示是否开启Nagle算法，true表示关闭，false表示开启，
        // 通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
        ;

        //表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);

        bind(bootstrap, 1000);
    }

    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功");
                } else {
                    System.err.println("端口[" + port + "]绑定失败");
                    bootstrap.bind(port + 1);
                }
            }
        });
    }
}
