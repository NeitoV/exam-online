package com.football.pitch.enumeration;

public enum EPeriod {
    Morning("morning"),
    Afternoon("afternoon"),
    Evening("evening"),
    MidNight("midnight");

    public static long morning = 1;
    public static long afternoon = 2;
    public static long evening = 3;
    public static long midnight = 4;

    private final String name;

    EPeriod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
