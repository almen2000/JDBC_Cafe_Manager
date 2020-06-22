package com.cafe.types;

public enum Gender {
    MALE("male"), FEMALE("female");

    private final String type;

    Gender(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Gender getEnumByValue(String value) {
        if (value.equals("male")) {
            return Gender.MALE;
        } else if (value.equals("female")) {
            return Gender.FEMALE;
        }
        throw new RuntimeException("There is no Enum with this value");
    }

    @Override
    public String toString() {
        return type;
    }
}
