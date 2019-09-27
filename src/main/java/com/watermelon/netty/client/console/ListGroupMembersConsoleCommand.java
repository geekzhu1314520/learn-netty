package com.watermelon.netty.client.console;

import com.watermelon.netty.protocol.request.ListGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入 groupId, 获取群成员列表");
        String groupId = scanner.next();
        ListGroupRequestPacket listGroupRequestPacket = new ListGroupRequestPacket();
        listGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupRequestPacket);
    }
}
