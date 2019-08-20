package com.watermelon.netty.protocol.request;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.command.Command;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
