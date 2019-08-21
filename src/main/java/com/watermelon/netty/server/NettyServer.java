package com.watermelon.netty.server;

import com.watermelon.netty.server.handler.inbound.InBoundHandlerA;
import com.watermelon.netty.server.handler.inbound.InBoundHandlerB;
import com.watermelon.netty.server.handler.inbound.InBoundHandlerC;
import com.watermelon.netty.server.handler.outbound.OutBoundHandlerA;
import com.watermelon.netty.server.handler.outbound.OutBoundHandlerB;
import com.watermelon.netty.server.handler.outbound.OutBoundHandlerC;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true) //表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.TCP_NODELAY, true) //表示是否开启Nagle算法，true表示关闭，false表示开启，
                .childHandler(new ChannelInitializer<NioSocketChannel>() {  //定义后续每条连接的数据读写，业务处理逻辑
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        //NioServerSocketChannel和NioSocketChannel是对NIO类型连接的抽象，
                        // 其概念可以和 BIO 编程模型中的ServerSocket以及Socket两个概念对应上

                        // inBound,处理读数据的逻辑链
                        ch.pipeline().addLast(new InBoundHandlerA());
                        ch.pipeline().addLast(new InBoundHandlerB());
                        ch.pipeline().addLast(new InBoundHandlerC());

                        // outBound,处理写数据的逻辑链
                        ch.pipeline().addLast(new OutBoundHandlerA());
                        ch.pipeline().addLast(new OutBoundHandlerB());
                        ch.pipeline().addLast(new OutBoundHandlerC());
                    }
                });
        bind(bootstrap, 8000);
    }

    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功");
            } else {
                System.err.println("端口[" + port + "]绑定失败");
//                bootstrap.bind(port + 1);
            }
        });
    }
}
