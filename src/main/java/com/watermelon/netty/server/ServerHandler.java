package com.watermelon.netty.server;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.PacketCodeC;
import com.watermelon.netty.protocol.request.LoginRequestPacket;
import com.watermelon.netty.protocol.request.MessageRequestPacket;
import com.watermelon.netty.protocol.response.LoginResponsePacket;
import com.watermelon.netty.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(buffer);

        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + ":客户端开始登录...");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(loginRequestPacket.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ":登录成功!");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码验证失败");
                System.out.println(new Date() + ":登录失败!");
            }

            ByteBuf out = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(out);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ":收到客户端消息->" + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf out = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(out);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
