package com.watermelon.netty.util;

import com.watermelon.netty.attribute.Attributes;
import com.watermelon.netty.session.Session;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class SessionUtil {

    private static Map<String, Channel> userChannelMap = new HashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.SESSION).get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userChannelMap.get(userId);
    }

}
