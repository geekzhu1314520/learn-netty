package com.watermelon.netty.protocol.response;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.command.Command;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
