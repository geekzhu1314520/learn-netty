package com.watermelon.netty.server.handler;

import com.watermelon.netty.protocol.request.SendToGroupRequestPacket;
import com.watermelon.netty.protocol.response.SendToGroupResponsePacket;
import com.watermelon.netty.session.Session;
import com.watermelon.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendToGroupRequestPacket requestPacket) throws Exception {

        String groupId = requestPacket.getGroupId();
        String message = requestPacket.getMessage();
        Session fromUser = SessionUtil.getSession(ctx.channel());

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            System.err.println("群[" + groupId + "]不存在！");
            return;
        }

        SendToGroupResponsePacket responsePacket = new SendToGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setMessage(message);
        responsePacket.setFromUser(fromUser);
        channelGroup.writeAndFlush(responsePacket);
    }
}
