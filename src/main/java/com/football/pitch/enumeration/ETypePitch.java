package com.football.pitch.enumeration;

public enum ETypePitch {

    FIVE_SIDE_PITCH("Five a side"),
    SEVEN_SIDE_PITCH("Seven a side"),
    ELEVEN_SIDE_PITCH("Eleven a side");

    public static int fiveSidePitch = 1;
    public static int sevenSidePitch = 2;
    public static int elevenSidePitch = 3;
    private final String name;

    ETypePitch(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
