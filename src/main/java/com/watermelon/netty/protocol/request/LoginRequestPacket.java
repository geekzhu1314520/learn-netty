package com.watermelon.netty.protocol.request;

import com.watermelon.netty.protocol.command.Command;
import com.watermelon.netty.protocol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
