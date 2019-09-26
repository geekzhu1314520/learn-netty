package com.watermelon.netty.client.handler;

import com.watermelon.netty.protocol.request.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        String groupId = quitGroupResponsePacket.getGroupId();
        System.out.println("退出群聊[" + groupId + "]成功!");
    }
}
