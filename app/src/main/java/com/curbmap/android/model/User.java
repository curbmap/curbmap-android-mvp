package com.curbmap.android.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${EMAIL} on 4/17/18.
 */


public class User {

    public static final String DEFAULT_USER_NAME = "curbmaptest";
    public static final String DEFAULT_USER_PASSWORD = "";

    @Getter
    @Setter
    String username;

    @Setter
    String email;

    @Getter
    @Setter
    String token;
}
