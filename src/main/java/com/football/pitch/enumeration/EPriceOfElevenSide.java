package com.football.pitch.enumeration;

public enum EPriceOfElevenSide {
    MORNING(1000),
    AFTERNOON(800),
    EVENING(1500),
    MIDNIGHT(1000);

    private final int value;

    EPriceOfElevenSide(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
