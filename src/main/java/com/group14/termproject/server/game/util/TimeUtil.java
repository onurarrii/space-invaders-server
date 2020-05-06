package com.group14.termproject.server.game.util;

public final class TimeUtil {

    private static final long NS_TO_MS_COEFFICIENT = 1000000;

    private TimeUtil() {
    }

    private static long getElapsedTimeInMs(long startTime) {
        long now = nsToMs(System.nanoTime());
        return now - startTime;
    }

    private static long nsToMs(long time) {
        return time / NS_TO_MS_COEFFICIENT;
    }

    public static boolean isEnoughTimePassed(long fromStartTime, long requiredElapsedTime) {
        return getElapsedTimeInMs(fromStartTime) > requiredElapsedTime;
    }

    public static long getCurrentTimeInMs() {
        return nsToMs(System.nanoTime());
    }
}
