package com.cafe.types;

public enum UserType {
    MANAGER("Manager"), WAITER("Waiter");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static UserType getEnumByValue(String value) {
        if (value.equals("Manager")) {
            return UserType.MANAGER;
        } else if (value.equals("Waiter")) {
            return UserType.WAITER;
        }
        throw new RuntimeException("There is no Enum with this value");
    }

    @Override
    public String toString() {
        return type;
    }
}
