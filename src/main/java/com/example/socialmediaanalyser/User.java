package com.example.socialmediaanalyser;

public class User {
    public String UserName;
    public String Password;

    public User(String UserName, String Password) {
        this.UserName = UserName;
        this.Password = Password;

    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

}
