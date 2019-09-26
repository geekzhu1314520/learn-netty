package com.watermelon.netty.client.console;

import com.watermelon.netty.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupComsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入 groupId，退出群聊：");
        String groupId = scanner.next();
        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();
        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
