package com.cafe.management;

import com.cafe.types.Gender;
import com.cafe.types.UserType;

import java.util.UUID;

abstract class User {
    protected UUID userId;
    protected String first_name;
    protected String last_name;
    protected String username;
    protected String password_hash;
    protected String email;
    protected Gender gender;
    protected UserType type;

    public User(UUID userId, String first_name, String last_name, String username, String password_hash, String email, Gender gender, UserType type) {
        this.userId = userId;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.gender = gender;
        this.type = type;
    }
}
