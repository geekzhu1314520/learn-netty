package com.watermelon.netty.protocol.request;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.command.Command;


public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
