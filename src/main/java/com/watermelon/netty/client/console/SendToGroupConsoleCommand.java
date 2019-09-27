package com.watermelon.netty.client.console;

import com.watermelon.netty.protocol.request.SendToGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个群组：");
        String groupId = scanner.next();
        String message = scanner.next();

        SendToGroupRequestPacket sendToGroupRequestPacket = new SendToGroupRequestPacket();
        sendToGroupRequestPacket.setGroupId(groupId);
        sendToGroupRequestPacket.setMessage(message);
        channel.writeAndFlush(sendToGroupRequestPacket);
    }

}
