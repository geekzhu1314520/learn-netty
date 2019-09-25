package com.watermelon.netty.util;

import java.util.UUID;

public class RandomUtil {

    public static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
