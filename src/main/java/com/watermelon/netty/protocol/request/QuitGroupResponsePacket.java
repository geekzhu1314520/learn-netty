package com.watermelon.netty.protocol.request;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.command.Command;
import lombok.Data;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
