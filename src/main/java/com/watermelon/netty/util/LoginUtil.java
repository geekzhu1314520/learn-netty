package com.watermelon.netty.util;

import com.watermelon.netty.attribute.Attributes;
import io.netty.channel.Channel;

public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.LOGIN).get() != null;
    }
}
