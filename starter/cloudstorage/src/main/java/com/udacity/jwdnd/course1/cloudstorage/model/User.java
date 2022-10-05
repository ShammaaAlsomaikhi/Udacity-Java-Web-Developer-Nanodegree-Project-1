package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private int userId;
    private String username;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

}
