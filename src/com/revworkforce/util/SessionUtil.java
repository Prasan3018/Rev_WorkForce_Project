package com.revworkforce.util;

public class SessionUtil {

    private static long lastActivityTime;
    private static final long TIMEOUT = 2 * 60 * 1000; // 2 minutes

    public static void updateActivity() {
        lastActivityTime = System.currentTimeMillis();
    }

    public static boolean isSessionExpired() {

        long currentTime = System.currentTimeMillis();

        return (currentTime - lastActivityTime) > TIMEOUT;
    }
}
