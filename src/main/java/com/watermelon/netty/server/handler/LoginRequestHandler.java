package com.watermelon.netty.server.handler;

import com.watermelon.netty.protocol.request.LoginRequestPacket;
import com.watermelon.netty.protocol.response.LoginResponsePacket;
import com.watermelon.netty.session.Session;
import com.watermelon.netty.util.RandomUtil;
import com.watermelon.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

//表明多个channel可以共享handler
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    //构造单例
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = RandomUtil.randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUsername() + "]登录成功");
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUsername()), ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验错误");
            System.out.println(new Date() + ": 登录失败！");
        }

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
