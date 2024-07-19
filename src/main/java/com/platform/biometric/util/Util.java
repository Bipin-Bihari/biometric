package com.platform.biometric.util;

public class Util {

    public static String convertSecondsToMinutes(long totalSecs) {
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        return (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
                + (seconds < 10 ? "0" : "") + seconds;
    }

    public static String dailyAttendance(int hours) {
        String attendence = null;
        if (hours < 4) {
            attendence = Commons.absentAttendance;
        } else if (hours >= 8) {
            attendence = Commons.presentAttendance;
        } else {
            attendence = Commons.halfDayAttendance;
        }
        return attendence;
    }
}
