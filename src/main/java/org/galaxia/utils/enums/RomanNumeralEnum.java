package org.galaxia.utils.enums;

public enum RomanNumeralEnum {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);

    private final int value;

    RomanNumeralEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RomanNumeralEnum fromString(String s) {
        return switch (s) {
            case "I" -> I;
            case "V" -> V;
            case "X" -> X;
            case "L" -> L;
            case "C" -> C;
            case "D" -> D;
            case "M" -> M;
            default -> null;
        };
    }
}
