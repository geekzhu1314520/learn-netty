package com.watermelon.netty.protocol.response;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.command.Command;
import com.watermelon.netty.session.Session;
import lombok.Data;

import java.util.List;

@Data
public class ListGroupResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
