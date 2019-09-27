package com.watermelon.netty.client.console;

import com.watermelon.netty.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup",  new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupComsoleCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {

        if (!SessionUtil.hasLogin(channel)) {
            return;
        }

        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand == null) {
            System.err.println("无法识别[" + command + "]命令，请重新输入");
        } else {
            consoleCommand.exec(scanner, channel);
        }
    }
}
