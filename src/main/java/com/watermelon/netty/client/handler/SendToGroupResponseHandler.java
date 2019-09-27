package com.watermelon.netty.client.handler;

import com.watermelon.netty.protocol.response.SendToGroupResponsePacket;
import com.watermelon.netty.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupResponsePacket responsePacket) throws Exception {
        String groupId = responsePacket.getGroupId();
        Session fromUser = responsePacket.getFromUser();
        String message = responsePacket.getMessage();
        System.out.println("收到群[" + groupId + "]中[" + fromUser + "]发来的消息:" + message);
    }
}
