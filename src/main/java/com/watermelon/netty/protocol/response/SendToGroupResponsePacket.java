package com.watermelon.netty.protocol.response;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.command.Command;
import com.watermelon.netty.session.Session;
import lombok.Data;

@Data
public class SendToGroupResponsePacket extends Packet {

    private String groupId;
    private Session fromUser;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_TO_GROUP_RESPONSE;
    }
}
