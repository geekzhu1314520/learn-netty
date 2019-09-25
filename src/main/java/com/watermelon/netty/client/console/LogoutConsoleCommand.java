package com.watermelon.netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutConsoleCommand logoutConsoleCommand = new LogoutConsoleCommand();
        channel.writeAndFlush(logoutConsoleCommand);
    }
}
