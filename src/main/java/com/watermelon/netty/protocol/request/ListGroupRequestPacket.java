package com.watermelon.netty.protocol.request;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.command.Command;
import lombok.Data;

@Data
public class ListGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
