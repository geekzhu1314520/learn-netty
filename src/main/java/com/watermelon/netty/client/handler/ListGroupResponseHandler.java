package com.watermelon.netty.client.handler;

import com.watermelon.netty.protocol.response.ListGroupResponsePacket;
import com.watermelon.netty.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class ListGroupResponseHandler extends SimpleChannelInboundHandler<ListGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupResponsePacket listGroupResponsePacket) throws Exception {

        if (!listGroupResponsePacket.isSuccess()) {
            System.out.println(listGroupResponsePacket.getReason());
            return;
        }
        String groupId = listGroupResponsePacket.getGroupId();
        List<Session> sessionList = listGroupResponsePacket.getSessionList();
        System.out.println("群[" + groupId + "]中的人包括:" + sessionList);
    }
}
