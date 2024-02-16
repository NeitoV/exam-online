package com.football.pitch.enumeration;

public enum EStatus {
    CREATED("Created"),
    CANCELED("Canceled"),
    DONE("Done");

    public static long created = 1;
    public static long canceled = 2;
    public static long done = 3;

    private final String name;

    EStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
