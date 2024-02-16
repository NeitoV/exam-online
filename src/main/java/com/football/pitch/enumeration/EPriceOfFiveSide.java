package com.football.pitch.enumeration;

public enum EPriceOfFiveSide {
    MORNING(200),
    AFTERNOON(180),
    EVENING(280),
    MIDNIGHT(200);

    private final int value;

    EPriceOfFiveSide(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
