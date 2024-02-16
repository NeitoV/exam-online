package com.football.pitch.enumeration;

public enum EPriceOfSevenSide {
    MORNING(300),
    AFTERNOON(250),
    EVENING(400),
    MIDNIGHT(300);

    private final int value;

    EPriceOfSevenSide(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
