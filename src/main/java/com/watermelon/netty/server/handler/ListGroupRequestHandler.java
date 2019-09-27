package com.watermelon.netty.server.handler;

import com.watermelon.netty.protocol.request.ListGroupRequestPacket;
import com.watermelon.netty.protocol.response.ListGroupResponsePacket;
import com.watermelon.netty.session.Session;
import com.watermelon.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class ListGroupRequestHandler extends SimpleChannelInboundHandler<ListGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupRequestPacket listGroupRequestPacket) throws Exception {

        String groupId = listGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        ListGroupResponsePacket listGroupResponsePacket = new ListGroupResponsePacket();
        if (channelGroup == null) {
            listGroupResponsePacket.setSuccess(false);
            listGroupResponsePacket.setReason("群[" + groupId + "]不存在!");
        } else {
            List<Session> sessionList = new ArrayList<>();
            for (Channel channel : channelGroup) {
                Session session = SessionUtil.getSession(channel);
                sessionList.add(session);
            }
            listGroupResponsePacket.setSuccess(true);
            listGroupResponsePacket.setSessionList(sessionList);
            listGroupResponsePacket.setGroupId(groupId);
        }
        ctx.channel().writeAndFlush(listGroupResponsePacket);
    }
}
