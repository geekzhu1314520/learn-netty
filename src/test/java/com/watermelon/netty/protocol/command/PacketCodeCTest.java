package com.watermelon.netty.protocol.command;

import com.watermelon.netty.protocol.Packet;
import com.watermelon.netty.protocol.PacketCodeC;
import com.watermelon.netty.protocol.request.LoginRequestPacket;
import com.watermelon.netty.serialize.Serializer;
import com.watermelon.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

public class PacketCodeCTest {

    @Test
    public void test() {
        Serializer serializer = new JSONSerializer();

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setVersion((byte) 1);
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUsername("Alan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT.ioBuffer(), loginRequestPacket);
        Packet decodePacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodePacket));
    }

}
