package com.watermelon.netty.client.console;

import com.watermelon.netty.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入用户名登录：");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        String username = scanner.nextLine();
        loginRequestPacket.setUsername(username);
        loginRequestPacket.setPassword("pwd");

        //发送登录数据报
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
