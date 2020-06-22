package com.cafe.types;

public enum Status {
    OPEN("open"), CANCELLED("cancelled"), CLOSED("closed");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status getEnumByValue(String value) {
        switch (value) {
            case "open":
                return Status.OPEN;
            case "cancelled":
                return Status.CANCELLED;
            case "closed":
                return Status.CLOSED;
            default:
                throw new RuntimeException("There is no Enum with this value");
        }
    }

    @Override
    public String toString() {
        return status;
    }
}
