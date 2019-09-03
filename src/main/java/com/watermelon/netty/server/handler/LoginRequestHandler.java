package com.watermelon.netty.server.handler;

import com.watermelon.netty.protocol.request.LoginRequestPacket;
import com.watermelon.netty.protocol.response.LoginResponsePacket;
import com.watermelon.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + ":客户端开始登录...");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + ":登录成功!");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码验证失败");
            System.out.println(new Date() + ":登录失败!");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
