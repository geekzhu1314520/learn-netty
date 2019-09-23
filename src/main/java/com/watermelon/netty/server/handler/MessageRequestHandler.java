package com.watermelon.netty.server.handler;

import com.watermelon.netty.protocol.request.MessageRequestPacket;
import com.watermelon.netty.protocol.response.MessageResponsePacket;
import com.watermelon.netty.session.Session;
import com.watermelon.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {

        //拿出发送方的信息
        Session session = SessionUtil.getSession(ctx.channel());

        //组装接收方的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(msg.getMessage());

        //拿出接收方信息
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        //发送消息
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + msg.getToUserId() + "]不在线，发送失败");
        }
    }
}
