package com.watermelon.netty.server.handler;

import com.watermelon.netty.protocol.request.QuitGroupRequestPacket;
import com.watermelon.netty.protocol.request.QuitGroupResponsePacket;
import com.watermelon.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if(channelGroup != null){
            channelGroup.remove(ctx.channel());
        }
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setSuccess(true);
        quitGroupResponsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
